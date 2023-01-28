package com.arifwidayana.pokemoncard.data.network.model.response

import com.google.gson.annotations.SerializedName

data class CardPokemonResponse(
    @SerializedName("artist")
    val artist: String?,
    @SerializedName("attacks")
    val attacks: List<Attack?>,
    @SerializedName("cardmarket")
    val cardMarket: CardMarket?,
    @SerializedName("convertedRetreatCost")
    val convertedRetreatCost: Int?,
    @SerializedName("evolvesTo")
    val evolvesTo: List<String?>,
    @SerializedName("flavorText")
    val flavorText: String?,
    @SerializedName("hp")
    val hp: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("images")
    val images: Images?,
    @SerializedName("legalities")
    val legalities: Legalities?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nationalPokedexNumbers")
    val nationalPokedexNumbers: List<Int?>,
    @SerializedName("number")
    val number: String?,
    @SerializedName("rarity")
    val rarity: String?,
    @SerializedName("retreatCost")
    val retreatCost: List<String?>,
    @SerializedName("set")
    val `set`: Set?,
    @SerializedName("subtypes")
    val subtypes: List<String?>,
    @SerializedName("supertype")
    val supertype: String?,
    @SerializedName("tcgplayer")
    val tcgPlayer: TcgPlayer?,
    @SerializedName("types")
    val types: List<String?>,
    @SerializedName("weaknesses")
    val weaknesses: List<Weaknesses?>
) {
    data class Attack(
        @SerializedName("convertedEnergyCost")
        val convertedEnergyCost: Int?,
        @SerializedName("cost")
        val cost: List<String?>?,
        @SerializedName("damage")
        val damage: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("text")
        val text: String?
    )

    data class CardMarket(
        @SerializedName("prices")
        val prices: Prices?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("url")
        val url: String?
    ) {
        data class Prices(
            @SerializedName("averageSellPrice")
            val averageSellPrice: Double?,
            @SerializedName("avg1")
            val avg1: Double?,
            @SerializedName("avg30")
            val avg30: Double?,
            @SerializedName("avg7")
            val avg7: Double?,
            @SerializedName("lowPrice")
            val lowPrice: Double?,
            @SerializedName("lowPriceExPlus")
            val lowPriceExPlus: Double?,
            @SerializedName("reverseHoloAvg1")
            val reverseHoloAvg1: Double?,
            @SerializedName("reverseHoloAvg30")
            val reverseHoloAvg30: Double?,
            @SerializedName("reverseHoloAvg7")
            val reverseHoloAvg7: Double?,
            @SerializedName("reverseHoloTrend")
            val reverseHoloTrend: Double?,
            @SerializedName("trendPrice")
            val trendPrice: Double?
        )
    }

    data class Images(
        @SerializedName("large")
        val large: String?,
        @SerializedName("small")
        val small: String?
    )

    data class Legalities(
        @SerializedName("unlimited")
        val unlimited: String?
    )

    data class Set(
        @SerializedName("id")
        val id: String?,
        @SerializedName("images")
        val images: Images?,
        @SerializedName("legalities")
        val legalities: Legalities?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("printedTotal")
        val printedTotal: Int?,
        @SerializedName("ptcgoCode")
        val ptcGoCode: String?,
        @SerializedName("releaseDate")
        val releaseDate: String?,
        @SerializedName("series")
        val series: String?,
        @SerializedName("total")
        val total: Int?,
        @SerializedName("updatedAt")
        val updatedAt: String?
    ) {
        data class Images(
            @SerializedName("logo")
            val logo: String?,
            @SerializedName("symbol")
            val symbol: String?
        )

        data class Legalities(
            @SerializedName("unlimited")
            val unlimited: String?
        )
    }

    data class TcgPlayer(
        @SerializedName("prices")
        val prices: Prices?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("url")
        val url: String?
    ) {
        data class Prices(
            @SerializedName("normal")
            val normal: Normal?
        ) {
            data class Normal(
                @SerializedName("directLow")
                val directLow: Double?,
                @SerializedName("high")
                val high: Double?,
                @SerializedName("low")
                val low: Double?,
                @SerializedName("market")
                val market: Double?,
                @SerializedName("mid")
                val mid: Double?
            )
        }
    }

    data class Weaknesses(
        @SerializedName("type")
        val type: String?,
        @SerializedName("value")
        val value: String?
    )
}