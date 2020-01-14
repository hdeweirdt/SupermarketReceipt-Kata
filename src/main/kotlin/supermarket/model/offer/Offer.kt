package supermarket.model.offer

import supermarket.model.Product

class Offer(internal var offerType: SpecialOfferType, internal val product: Product, internal var argument: Double) {

}

enum class SpecialOfferType {
    ThreeForTwo, TenPercentDiscount, TwoForAmount, FiveForAmount
}