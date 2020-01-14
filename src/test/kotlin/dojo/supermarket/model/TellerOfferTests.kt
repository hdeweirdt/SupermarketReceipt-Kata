package dojo.supermarket.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import supermarket.model.*
import supermarket.model.offer.SpecialOfferType

class TellerOfferTests {

    private val toothbrush = Product("toothbrush", ProductUnit.Each)
    private val apples = Product("apples", ProductUnit.Kilo)

    private fun getFilledCatalog(): SupermarketCatalog {
        val catalog = FakeCatalog()
        catalog.addProduct(toothbrush, 1.0)
        catalog.addProduct(apples, 2.0)
        return catalog
    }

    @Test
    fun fiveForAmountOfferIsOfferPriceWhenBuyingFive() {
        val catalog = getFilledCatalog()
        val offerPrice = 20.0

        val cart = ShoppingCart()
        cart.addToCart(apples, 5.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, offerPrice)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(offerPrice, receipt.totalPrice)
    }


    @Test
    fun fiveForAmountOfferAddsNormalPriceOnTopOfOfferPriceForMoreThanFive() {
        val catalog = getFilledCatalog()
        val offerPrice = 20.0

        val cart = ShoppingCart()
        cart.addToCart(apples, 6.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, offerPrice)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(offerPrice + 2.0, receipt.totalPrice)
    }

    @Test
    fun fiveForAmountIsNotAppliedWithLessThanFiveItems() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 3.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 5.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(6.0, receipt.totalPrice)
    }

    @Test
    fun tenPercentDiscountGetsApplied() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 4.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(7.20, receipt.totalPrice)
    }

    @Test
    fun twoForAmountOfferIsOfferPriceWhenBuyingTwo() {
        val catalog = getFilledCatalog()
        val offerPrice = 20.0

        val cart = ShoppingCart()
        cart.addToCart(apples, 2.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, offerPrice)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(offerPrice, receipt.totalPrice)
    }

    @Test
    fun twoForAmountOfferAddsNormalPriceOnTopOfOfferPriceForMoreThanTwo() {
        val catalog = getFilledCatalog()
        val offerPrice = 20.0

        val cart = ShoppingCart()
        cart.addToCart(apples, 3.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, offerPrice)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(offerPrice + 2.0, receipt.totalPrice)
    }

    @Test
    fun twoForAmountIsNotAppliedWithLessThanTwoItems() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 1.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 5.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(2.0, receipt.totalPrice)
    }

    @Test
    fun `TheeForTwo makes buying 3 items cost the same as two items`() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 3.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 5.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(4.0, receipt.totalPrice)
    }

    @Test
    fun `TheeForTwo makes buying 6 items cost the same as four items`() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 6.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 5.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(8.0, receipt.totalPrice)
    }

    @Test
    fun `TheeForTwo makes buying 4 items cost the same as two and 1 items`() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addToCart(apples, 4.0)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 5.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(6.0, receipt.totalPrice)
    }
}

