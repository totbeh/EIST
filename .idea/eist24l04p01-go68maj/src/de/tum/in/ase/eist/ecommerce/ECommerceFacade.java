package de.tum.in.ase.eist.ecommerce;

public class ECommerceFacade {
    private final OrderController orderController;
    private final AdvertisementController advertisementController;
    private final ShippingController shippingController;

    public ECommerceFacade() {
        this.orderController = new OrderController();
        this.advertisementController = new AdvertisementController();
        this.shippingController = new ShippingController();
    }

    public void processOrder(Order order) {
        orderController.processOrder(order);
    }

    public void processOrder(Order order, String phoneNumber) {
        orderController.processOrder(order, phoneNumber);
    }

    public Order retrieveLatestOrder(int id) {
        return orderController.retrieveLatestOrder(id);
    }

    public void playAdvertisement(int ageRestriction) {
        advertisementController.playAdvertisement(ageRestriction);
    }

    public void shipOrder(Order order, String string) {
        Shipping shipping = shippingController.createShipping(string);
        order.setShipping(shipping);
        shippingController.shipOrder(order);
    }
}
