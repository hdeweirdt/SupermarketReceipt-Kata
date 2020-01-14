package supermarket.model.offer

import supermarket.model.Discount
import supermarket.model.Product
import supermarket.model.ProductQuantity
import supermarket.model.SupermarketCatalog

abstract class Offer(
    internal val product: Product,
    internal var argument: Double
) {
    abstract fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount?
}

class TwoForAmount(product: Product, argument: Double) : Offer(product, argument) {
    override fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount? {
        val quantityAsInt = productQuantity.quantity.toInt()
        return if (quantityAsInt >= 2) {
            val unitPrice = catalog.getUnitPrice(productQuantity.product)
            val productQuantityPerOfferApplication = 2
            val total =
                argument * (quantityAsInt / productQuantityPerOfferApplication) + quantityAsInt % 2 * unitPrice
            val discountN = unitPrice * productQuantity.quantity - total
            Discount(productQuantity.product, "2 for $argument", discountN)
        } else {
            null
        }
    }
}

class TenPercent(product: Product, argument: Double) : Offer(product, argument) {
    override fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount? {
        val quantity = productQuantity.quantity
        val unitPrice = catalog.getUnitPrice(productQuantity.product)
        return Discount(
            productQuantity.product,
            "$argument% off",
            quantity * unitPrice * argument / 100.0
        )
    }

}

class FiveForAmount(product: Product, argument: Double) : Offer(product, argument) {

    override fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount? {
        return if (productQuantity.quantity >= 5) {
            val quantityAsInt = productQuantity.quantity.toInt()
            val productQuantityPerOfferApplication = 5
            val unitPrice = catalog.getUnitPrice(productQuantity.product)
            val timesToRepeatOffer = quantityAsInt / productQuantityPerOfferApplication
            val discountTotal =
                unitPrice * productQuantity.quantity - (argument * timesToRepeatOffer + quantityAsInt % 5 * unitPrice)
            Discount(
                productQuantity.product,
                "$productQuantityPerOfferApplication for $argument",
                discountTotal
            )
        } else {
            null
        }
    }
}

class ThreeForTwo(product: Product, argument: Double) : Offer(product, argument) {
    override fun calculateDiscount(productQuantity: ProductQuantity, catalog: SupermarketCatalog): Discount? {
        val quantityAsInt = productQuantity.quantity.toInt()
        return if (quantityAsInt >= 3) {
            val productQuantityPerOfferApplication = 3
            val unitPrice = catalog.getUnitPrice(productQuantity.product)
            val timesToRepeatOffer = quantityAsInt / productQuantityPerOfferApplication
            val discountAmount =
                productQuantity.quantity * unitPrice - (timesToRepeatOffer.toDouble() * 2.0 * unitPrice + quantityAsInt % 3 * unitPrice)
            Discount(productQuantity.product, "3 for 2", discountAmount)
        } else {
            null
        }
    }
}

enum class SpecialOfferType {
    ThreeForTwo, TenPercentDiscount, TwoForAmount, FiveForAmount
}