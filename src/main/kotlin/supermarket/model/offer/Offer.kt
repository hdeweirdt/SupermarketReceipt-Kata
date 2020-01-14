package supermarket.model.offer

import supermarket.model.Discount
import supermarket.model.Product
import supermarket.model.ProductQuantity
import supermarket.model.SupermarketCatalog

class Offer(internal var offerType: SpecialOfferType, internal val product: Product, internal var argument: Double) {
    fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount {
        val quantity = productQuantity.quantity
        val unitPrice = catalog.getUnitPrice(productQuantity.product)
        val quantityAsInt = quantity.toInt()
        var productQuantityPerOfferApplication = 1
        if (offerType === SpecialOfferType.ThreeForTwo) {
            productQuantityPerOfferApplication = 3

        } else if (offerType === SpecialOfferType.TwoForAmount) {
            productQuantityPerOfferApplication = 2
            if (quantityAsInt >= 2) {
                val total =
                    argument * (quantityAsInt / productQuantityPerOfferApplication) + quantityAsInt % 2 * unitPrice
                val discountN = unitPrice * quantity - total
                return Discount(productQuantity.product, "2 for $argument", discountN)
            }

        }
        if (offerType === SpecialOfferType.FiveForAmount) {
            productQuantityPerOfferApplication = 5
        }
        val timesToRepeatOffer = quantityAsInt / productQuantityPerOfferApplication
        if (offerType === SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            val discountAmount =
                quantity * unitPrice - (timesToRepeatOffer.toDouble() * 2.0 * unitPrice + quantityAsInt % 3 * unitPrice)
            return Discount(productQuantity.product, "3 for 2", discountAmount)
        }
        if (offerType === SpecialOfferType.TenPercentDiscount) {
            return Discount(
                productQuantity.product,
                "$argument% off",
                quantity * unitPrice * argument / 100.0
            )
        }
        if (offerType === SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            val discountTotal =
                unitPrice * quantity - (argument * timesToRepeatOffer + quantityAsInt % 5 * unitPrice)
            return Discount(
                productQuantity.product,
                "$productQuantityPerOfferApplication for $argument",
                discountTotal
            )
        }
        // TODO: remove this zero discount
        return Discount(productQuantity.product, productQuantityPerOfferApplication.toString(), 0.0)
    }

}

enum class SpecialOfferType {
    ThreeForTwo, TenPercentDiscount, TwoForAmount, FiveForAmount
}