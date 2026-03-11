package MarsAir.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static MarsAir.data.TestConstants.*;

@Epic("MarsAir Booking System")
@Feature("Flight Search Logic")
public class SearchFlightTests extends BaseTest {


    @Test(description = "TC01: Valid Promo Code Discount")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that seat is available and 30% discount is applied with code AF3-FJK-418")
    void shouldSeeDiscountWhenSearchWithValidPromoCode() {
        searchPage.searchFlight("July", "December (two years from now)", "AF3-FJK-418");
        Assert.assertEquals(searchPage.getConfirmSeatMessage(), "Seats available!");
        Assert.assertEquals(searchPage.getDiscountResult(TIMEOUT_MEDIUM), "Promotional code AF3-FJK-418 used: 30% discount!");
        Assert.assertEquals(searchPage.getBookingPhoneNumber(), "Call now on 0800 MARSAIR to book!");

    }

    @Test(description = "TC02: Search without Promo Code")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that seat is available but no discount is displayed when promo code is empty")
    void shouldSeeSeatsAvailableWhenSearchWithoutPromoCode() {
        searchPage.searchFlight("July", "December (two years from now)", "");
        Assert.assertEquals(searchPage.getConfirmSeatMessage(), "Seats available!");
        Assert.assertEquals(searchPage.getDiscountResult(TIMEOUT_SHORT), "");
        Assert.assertEquals(searchPage.getBookingPhoneNumber(), "Call now on 0800 MARSAIR to book!");
    }

    @Test(description = "TC03: Flight Full Scenario")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify system shows 'no more seats' message for full flights")
    void shouldSeeNoSeatsMessageWhenFlightIsFull() {
        searchPage.searchFlight("July", "July (two years from now)", "AF3-FJK-418");
        Assert.assertEquals(searchPage.getConfirmSeatMessage(), "Sorry, there are no more seats available.");
    }


    @Test(description = "TC04: Invalid Date Range")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify error message when return date is before departure date")
    void shouldSeeErrorMessageWhenSearchInvalidDateRange(){
        searchPage.searchFlight("July", "December", "");
        Assert.assertEquals(searchPage.getConfirmSeatMessage(), "Unfortunately, this schedule is not possible. Please try again.");
    }

    @Test(description = "TC05: Wrong Promo Code Format")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify system rejects invalid promo code format ABC-FJK-418")
    void shouldSeeInvalidCodeMessageWhenUseWrongPromoCode() {
        searchPage.searchFlight("July", "December (two years from now)", "ABC-FJK-418");
        Assert.assertEquals(searchPage.getConfirmSeatMessage(), "Seats available!");
        Assert.assertEquals(searchPage.getDiscountResult(TIMEOUT_MEDIUM), "Sorry, code ABC-FJK-418 is not valid");
        Assert.assertEquals(searchPage.getBookingPhoneNumber(), "Call now on 0800 MARSAIR to book!");

    }
}
