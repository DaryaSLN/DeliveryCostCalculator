import org.example.CargoDimension;
import org.example.DeliveryCalculator;
import org.example.DeliveryServiceLoad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTests {
    @ParameterizedTest
    @CsvSource({"1, SMALL, false, LOW",
            "2, SMALL, false, NORMAL",
            "1, LARGE, false, LOW"})
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Minimal delivery cost test")
    public void minimalDeliveryCostTest(double destinationDistance, CargoDimension cargoDimension, boolean isFragile,
                                        DeliveryServiceLoad deliveryServiceLoad) {
        DeliveryCalculator deliveryCalculator = new DeliveryCalculator(destinationDistance, cargoDimension, isFragile,
                deliveryServiceLoad);

        assertEquals(DeliveryCalculator.MINIMAL_DELIVERY_COST, deliveryCalculator.getTotalDeliveryCost());
    }

    @ParameterizedTest
    @CsvSource({"0.01, LARGE, true, VERY_HIGH, 880",
            "2, SMALL, false, HIGH, 400",
            "2.01, LARGE, false, INCREASED, 400",
            "10, SMALL, true, NORMAL, 500",
            "10.01, LARGE, true, LOW, 700",
            "30, SMALL, true, VERY_HIGH, 960",
            "30.01, LARGE, false, HIGH, 700",
            "20038, SMALL, false, INCREASED, 480"})
    @Tags({@Tag("boundary"), @Tag("positive")})
    @DisplayName("Boundary destination distance delivery cost test")
    public void boundaryDeliveryCostPositiveTest(double destinationDistance, CargoDimension cargoDimension,
                                                 boolean isFragile, DeliveryServiceLoad deliveryServiceLoad,
                                                 double expectedTotalDeliveryCost) {
        DeliveryCalculator deliveryCalculator = new DeliveryCalculator(destinationDistance, cargoDimension, isFragile,
                deliveryServiceLoad);

        assertEquals(expectedTotalDeliveryCost, deliveryCalculator.getTotalDeliveryCost());
    }

    @Test
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Inability to deliver fragile cargo at more than 30 km distance test")
    public void fragileCargoAndLongDistanceTest() {
        final String expectedErrorMessage = "Fragile goods cannot be delivered over a distance of more than 30 km.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new DeliveryCalculator(30.01, CargoDimension.LARGE, true, DeliveryServiceLoad.HIGH));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"0, LARGE, true, VERY_HIGH",
            "-1, SMALL, false, HIGH",
            "20038.01, LARGE, false, INCREASED"})
    @Tag("negative")
    @DisplayName("Incorrect destination distance delivery cost test")
    public void incorrectDestinationDistanceTest(double destinationDistance, CargoDimension cargoDimension,
                                                 boolean isFragile, DeliveryServiceLoad deliveryServiceLoad) {
        final String expectedErrorMessage = "Incorrect destination distance";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new DeliveryCalculator(destinationDistance, cargoDimension, isFragile, deliveryServiceLoad));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Null cargo dimension value test")
    public void nullCargoDimensionValueTest() {
        final String expectedErrorMessage = "Cargo dimension cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new DeliveryCalculator(5, null,
                true, DeliveryServiceLoad.LOW));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Null delivery service load value test")
    public void nullDeliveryServiceLoadValueTest() {
        final String expectedErrorMessage = "Delivery service load cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new DeliveryCalculator(5, CargoDimension.SMALL,
                true, null));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
