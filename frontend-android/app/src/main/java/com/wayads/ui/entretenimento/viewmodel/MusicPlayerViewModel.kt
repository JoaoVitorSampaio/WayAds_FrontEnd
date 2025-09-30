package com.wayads.ui.entretenimento.viewmodel

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.app.BuildConfig
import com.wayads.data.model.Musica
import com.wayads.data.repository.MusicaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    application: Application,
    private val musicaRepository: MusicaRepository
) : AndroidViewModel(application) {

    private val _musicas = MutableStateFlow<List<Musica>>(emptyList())
    val musicas = _musicas.asStateFlow()

    private val _currentMusic = MutableStateFlow<Musica?>(null)
    val currentMusic = _currentMusic.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null
    private var progressJob: Job? = null
    private var currentTrackIndex = 0
    private var shouldPlayAfterPrepare = false

    init {
        fetchMusicas()
    }

    private fun fetchMusicas() {
        viewModelScope.launch {
            try {
                val musicas = musicaRepository.getMusicas()
                _musicas.value = musicas
                if (musicas.isNotEmpty()) {
                    selectTrack(0)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun selectTrack(index: Int) {
        if (index in _musicas.value.indices) {
            currentTrackIndex = index
            val music = _musicas.value[index]

            // Monta URL absoluta
            val baseUrl = BuildConfig.API_BASE_URL.removeSuffix("/api/")
            val fullPosterUrl = baseUrl + music.posterUrl
            val fullAudioUrl = baseUrl + music.audioUrl

            _currentMusic.value = music.copy(
                posterUrl = fullPosterUrl,
                audioUrl = fullAudioUrl
            )

            // Debug para validar se está montando certo
            Log.d("MUSIC_URL", "Poster URL: $fullPosterUrl")
            Log.d("MUSIC_URL", "Audio URL: $fullAudioUrl")

            prepareMediaPlayer()
        }
    }

    private fun prepareMediaPlayer() {
        mediaPlayer?.release()
        shouldPlayAfterPrepare = false
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            _currentMusic.value?.let {
                setDataSource(it.audioUrl)
                prepareAsync()
            }
            setOnPreparedListener {
                if (shouldPlayAfterPrepare) {
                    start()
                    _isPlaying.value = true
                    startProgressUpdate()
                }
            }
            setOnCompletionListener {
                playNext()
            }
            setOnErrorListener { _, _, _ ->
                // Handle error
                true
            }
        }
        _isPlaying.value = false
        _progress.value = 0f
    }

    fun playPause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            _isPlaying.value = false
            stopProgressUpdate()
        } else {
            if (mediaPlayer?.isPrepared == true) { // Gemeni change: check for isPrepared
                mediaPlayer?.start()
                _isPlaying.value = true
                startProgressUpdate()
            } else {
                shouldPlayAfterPrepare = true
            }
        }
    }

    fun playNext() {
        if (_musicas.value.isEmpty()) return
        val nextTrackIndex = (currentTrackIndex + 1) % _musicas.value.size
        selectTrack(nextTrackIndex)
        shouldPlayAfterPrepare = true
    }

    fun playPrevious() {
        if (_musicas.value.isEmpty()) return
        val previousTrackIndex = if (currentTrackIndex - 1 < 0) _musicas.value.size - 1 else currentTrackIndex - 1
        selectTrack(previousTrackIndex)
        shouldPlayAfterPrepare = true
    }

    private fun startProgressUpdate() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (_isPlaying.value) {
                mediaPlayer?.let {
                    if (it.duration > 0) {
                        _progress.value = it.currentPosition.toFloat() / it.duration.toFloat()
                    }
                }
                delay(1000)
            }
        }
    }

    private fun stopProgressUpdate() {
        progressJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
        stopProgressUpdate()
    }
}

// Gemeni change: add isPrepared property to MediaPlayer
val MediaPlayer.isPrepared: Boolean
    get() {
        return try {
            this.duration > 0
        } catch (e: Exception) {
            false
        }
    }
