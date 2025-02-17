package org.example;

public class DeliveryCalculator {
    private double totalDeliveryCost;
    private double destinationDistance;
    private CargoDimension cargoDimension;
    private boolean isFragile;
    private DeliveryServiceLoad deliveryServiceLoad;
    public static final double MINIMAL_DELIVERY_COST = 400.0;

    public DeliveryCalculator(double destinationDistance, CargoDimension cargoDimension,
                              boolean isFragile, DeliveryServiceLoad deliveryServiceLoad) {
        this.destinationDistance = destinationDistance;
        this.cargoDimension = cargoDimension;
        this.isFragile = isFragile;
        this.deliveryServiceLoad = deliveryServiceLoad;
    }

    public double getTotalDeliveryCost() {
        return totalDeliveryCost;
    }

    public double getDestinationDistance() {
        return destinationDistance;
    }

    public void setDestinationDistance(double destinationDistance) {
        this.destinationDistance = destinationDistance;
    }

    public CargoDimension getCargoDimension() {
        return cargoDimension;
    }

    public void setCargoDimension(CargoDimension cargoDimension) {
        this.cargoDimension = cargoDimension;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public DeliveryServiceLoad getDeliveryServiceLoad() {
        return deliveryServiceLoad;
    }

    public void setDeliveryServiceLoad(DeliveryServiceLoad deliveryServiceLoad) {
        this.deliveryServiceLoad = deliveryServiceLoad;
    }

    public void calculateTotalDeliveryCost() {
        if (destinationDistance <= 2) {
            totalDeliveryCost += 50.0;
        } else if (destinationDistance <= 10) {
            totalDeliveryCost += 100.0;
        } else if (destinationDistance <= 30) {
            totalDeliveryCost += 200.0;
        } else {
            totalDeliveryCost += 300.0;
        }

        switch (cargoDimension) {
            case LARGE -> totalDeliveryCost += 200.0;
            case SMALL -> totalDeliveryCost += 100.0;
        }

        if (isFragile) {
            if (destinationDistance > 30) {
                throw new IllegalArgumentException("Fragile goods cannot be delivered over a distance of more than 30 km.");
            }
            totalDeliveryCost += 300.0;
        }

        switch (deliveryServiceLoad) {
            case VERY_HIGH -> totalDeliveryCost *= 1.6;
            case HIGH -> totalDeliveryCost *= 1.4;
            case INCREASED -> totalDeliveryCost *= 1.2;
            default -> totalDeliveryCost *= 1;
        }

        if (totalDeliveryCost < MINIMAL_DELIVERY_COST) {
            totalDeliveryCost = MINIMAL_DELIVERY_COST;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryCalculator that = (DeliveryCalculator) o;

        if (Double.compare(that.totalDeliveryCost, totalDeliveryCost) != 0) return false;
        if (Double.compare(that.destinationDistance, destinationDistance) != 0) return false;
        if (isFragile != that.isFragile) return false;
        if (cargoDimension != that.cargoDimension) return false;
        return deliveryServiceLoad == that.deliveryServiceLoad;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(totalDeliveryCost);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(destinationDistance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cargoDimension.hashCode();
        result = 31 * result + (isFragile ? 1 : 0);
        result = 31 * result + deliveryServiceLoad.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Following your request:\n" + "Destination distance = " + destinationDistance + " km" +
                ",\ncargo dimension = " + cargoDimension +
                ",\nis fragile = " + isFragile +
                ",\ndelivery service load = " + deliveryServiceLoad +
                "\nThe total delivery cost is: " + totalDeliveryCost + " rubles." ;
    }
}
