package com.wayads.ui.gastronomia.model

import com.wayads.app.R

/**
 * Enumeração para categorias de comida
 */
enum class CategoriaComida(val displayName: String) {
    ITALIANA("Italiana"),
    BRASILEIRA("Brasileira"),
    JAPONESA("Japonesa"),
    FAST_FOOD("Fast Food"),
    DOCES("Doces"),
    CHURRASCARIA("Churrascaria"),
    PIZZARIA("Pizzaria"),
    LANCHONETE("Lanchonete"),
    VEGETARIANA("Vegetariana"),
    TODOS("Todos")
}

/**
 * Enumeração para categorias gastronômicas
 */
enum class RegiaoGeografica(val displayName: String) {
    RECEITAS("Receitas"),
    ORIENTAL("Oriental"),
    REGIONAL("Regional"),
    MASSAS("Massas"),
    TODAS("Todas")
}

/**
 * Modelo de dados para anúncios gastronômicos
 */
data class AnuncioGastronomico(
    val id: Int,
    val titulo: String,
    val categoria: CategoriaComida,
    val regiao: RegiaoGeografica,
    val imagemRes: Int,
    val promocao: String,
    val pratoEspecial: String,
    val experienciaUnica: String,
    val desconto: String? = null,
    val validadePromocao: String? = null,
    val preco: String? = null,
    val endereco: String = "",
    val telefone: String = ""
)

/**
 * Modelo de dados para receitas gastronômicas
 */
data class ReceitaGastronomica(
    val id: Int,
    val titulo: String,
    val imagemRes: Int,
    val tempoPreparo: String,
    val rendimento: String,
    val dificuldade: String = "Fácil",
    val ingredientes: List<String>,
    val modoPreparo: List<String>,
    val destaque: String? = null
)

/**
 * Dados de exemplo para anúncios gastronômicos
 */
object GastronomiaData {
    val anuncios = listOf(
        AnuncioGastronomico(
            id = 1,
            titulo = "🍕 Pizzaria Bella Vista - Noite Italiana!",
            categoria = CategoriaComida.PIZZARIA,
            regiao = RegiaoGeografica.MASSAS,
            imagemRes = R.drawable.subway,
            promocao = "Rodízio de pizzas artesanais por apenas R$ 39,90!",
            pratoEspecial = "Pizza Margherita com mussarela de búfala importada",
            experienciaUnica = "Ambiente romântico com música italiana ao vivo às sextas",
            desconto = "30% OFF",
            validadePromocao = "Sextas e sábados",
            preco = "R$ 39,90",
            endereco = "Rua das Flores, 123 - Centro",
            telefone = "(11) 1234-5678"
        ),
        AnuncioGastronomico(
            id = 2,
            titulo = "🍣 Sushi Zen - Festival de Sashimi!",
            categoria = CategoriaComida.JAPONESA,
            regiao = RegiaoGeografica.ORIENTAL,
            imagemRes = R.drawable.subway,
            promocao = "Combinado especial: 30 peças de sushi + temaki por R$ 89,90",
            pratoEspecial = "Sashimi de salmão selvagem do Alaska",
            experienciaUnica = "Prepare seu próprio temaki na mesa com chef japonês",
            desconto = "25% OFF",
            validadePromocao = "Terças e quartas",
            preco = "R$ 89,90",
            endereco = "Av. Oriental, 456 - Liberdade",
            telefone = "(11) 2345-6789"
        ),
        AnuncioGastronomico(
            id = 3,
            titulo = "🥩 Churrascaria Gaúcha - Festa da Carne!",
            categoria = CategoriaComida.CHURRASCARIA,
            regiao = RegiaoGeografica.REGIONAL,
            imagemRes = R.drawable.subway,
            promocao = "Rodízio completo + buffet + sobremesa por R$ 69,90",
            pratoEspecial = "Picanha Premium na brasa com tempero especial da casa",
            experienciaUnica = "Show de churrasco ao vivo com música sertaneja",
            desconto = "20% OFF",
            validadePromocao = "Domingos em família",
            preco = "R$ 69,90",
            endereco = "Rua do Sul, 789 - Vila Madalena",
            telefone = "(11) 3456-7890"
        ),
        AnuncioGastronomico(
            id = 4,
            titulo = "🍔 Burger House - Mega Promoção!",
            categoria = CategoriaComida.FAST_FOOD,
            regiao = RegiaoGeografica.RECEITAS,
            imagemRes = R.drawable.subway,
            promocao = "Compre 1 combo e ganhe 50% OFF no segundo!",
            pratoEspecial = "Burger Bacon Cheddar com batata rústica",
            experienciaUnica = "Monte seu burger personalizado com mais de 15 ingredientes",
            desconto = "50% OFF",
            validadePromocao = "Segunda a quinta",
            preco = "A partir de R$ 24,90",
            endereco = "Av. Norte, 321 - Santana",
            telefone = "(11) 4567-8901"
        ),
        AnuncioGastronomico(
            id = 5,
            titulo = "🍝 Cantina da Nonna - Sabores da Itália!",
            categoria = CategoriaComida.ITALIANA,
            regiao = RegiaoGeografica.MASSAS,
            imagemRes = R.drawable.subway,
            promocao = "Menu executivo italiano completo por R$ 32,90",
            pratoEspecial = "Lasanha da Nonna com 4 queijos e molho especial",
            experienciaUnica = "Aula de culinária italiana aos sábados - aprenda a fazer massa fresca",
            desconto = "15% OFF",
            validadePromocao = "Segunda a sexta",
            preco = "R$ 32,90",
            endereco = "Rua Italiana, 654 - Bela Vista",
            telefone = "(11) 5678-9012"
        ),
        AnuncioGastronomico(
            id = 6,
            titulo = "🇧🇷 Feijoada da Casa - Tradição Brasileira!",
            categoria = CategoriaComida.BRASILEIRA,
            regiao = RegiaoGeografica.REGIONAL,
            imagemRes = R.drawable.subway,
            promocao = "Feijoada completa + caipirinha por R$ 45,90 aos sábados",
            pratoEspecial = "Feijoada tradicional com linguiça artesanal da casa",
            experienciaUnica = "Roda de samba ao vivo todos os sábados a partir das 14h",
            desconto = "25% OFF",
            validadePromocao = "Sábados",
            preco = "R$ 45,90",
            endereco = "Rua Brasil, 987 - Centro Histórico",
            telefone = "(11) 6789-0123"
        ),
        AnuncioGastronomico(
            id = 7,
            titulo = "🧁 Doceria Doce Vida - Semana do Chocolate!",
            categoria = CategoriaComida.DOCES,
            regiao = RegiaoGeografica.RECEITAS,
            imagemRes = R.drawable.subway,
            promocao = "Compre 2 bolos e leve 3! Válido para todos os sabores",
            pratoEspecial = "Bolo de Chocolate Belga com recheio de morango",
            experienciaUnica = "Workshop de confeitaria aos sábados - aprenda a fazer brigadeiros gourmet",
            desconto = "33% OFF",
            validadePromocao = "Semana toda",
            preco = "A partir de R$ 18,90",
            endereco = "Av. Doce, 147 - Mooca",
            telefone = "(11) 7890-1234"
        ),
        AnuncioGastronomico(
            id = 8,
            titulo = "🥪 Lanchonete do Bairro - Happy Hour!",
            categoria = CategoriaComida.LANCHONETE,
            regiao = RegiaoGeografica.RECEITAS,
            imagemRes = R.drawable.subway,
            promocao = "Happy Hour: 2 lanches + 2 sucos por R$ 29,90",
            pratoEspecial = "X-Tudo da Casa com ingredientes frescos",
            experienciaUnica = "Ambiente retrô com jogos clássicos e música dos anos 80",
            desconto = "40% OFF",
            validadePromocao = "17h às 19h",
            preco = "R$ 29,90",
            endereco = "Rua do Bairro, 258 - Vila Olímpia",
            telefone = "(11) 8901-2345"
        ),
        AnuncioGastronomico(
            id = 9,
            titulo = "🥗 Green Garden - Vida Saudável!",
            categoria = CategoriaComida.VEGETARIANA,
            regiao = RegiaoGeografica.RECEITAS,
            imagemRes = R.drawable.subway,
            promocao = "Menu executivo vegano completo por apenas R$ 24,90",
            pratoEspecial = "Hambúrguer de quinoa com batata doce assada",
            experienciaUnica = "Horta orgânica no local - colha seus próprios temperos",
            desconto = "15% OFF",
            validadePromocao = "Segunda a sexta",
            preco = "R$ 24,90",
            endereco = "Rua Verde, 369 - Centro",
            telefone = "(11) 9012-3456"
        )
    )

    val receitas = listOf(
        ReceitaGastronomica(
            id = 1,
            titulo = "🍝 Receita: Lasanha de 4 Queijos",
            imagemRes = R.drawable.subway,
            tempoPreparo = "50 min",
            rendimento = "6 porções",
            ingredientes = listOf(
                "500g de massa para lasanha",
                "400g de queijo muçarela",
                "200g de queijo provolone",
                "200g de queijo gorgonzola",
                "200g de parmesão ralado",
                "Molho branco a gosto"
            ),
            modoPreparo = listOf(
                "Monte camadas de massa, molhos e queijos.",
                "Finalize com parmesão e leve ao forno por 30 min a 200°C."
            ),
            destaque = "Perfeita para amantes de Massas"
        ),
        ReceitaGastronomica(
            id = 2,
            titulo = "🥢 Receita: Sushi Califórnia Caseiro",
            imagemRes = R.drawable.subway,
            tempoPreparo = "40 min",
            rendimento = "24 peças",
            ingredientes = listOf(
                "2 xícaras de arroz para sushi",
                "Nori, pepino, manga e kani",
                "Vinagre de arroz e açúcar"
            ),
            modoPreparo = listOf(
                "Cozinhe o arroz e tempere.",
                "Abra a alga, espalhe o arroz e os recheios, enrole e fatie."
            ),
            destaque = "Ideal para a categoria Oriental"
        ),
        ReceitaGastronomica(
            id = 3,
            titulo = "🍫 Receita: Brigadeiro Gourmet",
            imagemRes = R.drawable.subway,
            tempoPreparo = "20 min",
            rendimento = "20 unidades",
            ingredientes = listOf(
                "1 lata de leite condensado",
                "2 colheres de sopa de cacau 70%",
                "1 colher de sopa de manteiga",
                "Confeitos para finalizar"
            ),
            modoPreparo = listOf(
                "Cozinhe em fogo baixo até desgrudar do fundo.",
                "Deixe esfriar, enrole e passe nos confeitos."
            ),
            destaque = "Clássico brasileiro para a categoria Receitas"
        )
    )
}