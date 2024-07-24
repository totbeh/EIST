package eist;


import java.util.List;

public class FactoryFacade {

    private AccessControlList ACL;
    private InventoryClient inventoryClient;
    private ShippingClient shippingClient;

    //TODO: initialize variables in the constructor. AccessControlList instance should be filled according to Role/Permission table.
    public FactoryFacade(){
        this.ACL = new AccessControlList();
        ACL.grantAccess("SalesManager","Add");
        ACL.grantAccess("SalesManager","Sell");
        ACL.grantAccess("SalesManager","Check");
        ACL.grantAccess("SalesIntern","Add");
        ACL.grantAccess("SalesIntern","Check");
        ACL.grantAccess("MarketingManager","Check");
        this.inventoryClient = new InventoryClient();
        this.shippingClient = new ShippingClient();




    }

    //TODO: implement addProduct method with calling necessary methods from InventoryClient and/or ShippingClient. Do permission checks and follow return message if permission fails.
    public String addProduct(String role, int product){
        if (ACL.hasAccess(role,"Add")){
            inventoryClient.addProduct(product);
            return inventoryClient.getMessages();
        }
        return "This role has no access";
    }

    //TODO: implement sellProduct method with calling necessary methods from InventoryClient and/or ShippingClient. Do permission checks and follow return message if permission fails.
    public String sellProduct(String role, String shippingAddress, int product){
        if (ACL.hasAccess(role,"Sell")){
            shippingClient.makeShipping(shippingAddress);
            inventoryClient.removeProduct(product);
            return inventoryClient.getMessages();
        }
            return "This role has no access";
    }

    //TODO: implement checkProduct method with calling necessary methods from InventoryClient and/or ShippingClient. Do permission checks and follow return message if permission fails.
    public String checkProduct(String role){
        if (ACL.hasAccess(role,"Check")){
            inventoryClient.checkProduct();
            return inventoryClient.getMessages();
        }

            return  "This role has no access";
    }

    //TODO: implement shippingRecord method with calling necessary methods from InventoryClient and/or ShippingClient. Do permission checks and follow return message if permission fails.
    //TODO: be aware that only people who have "Sell" permission can access to the shippingRecords.
    public String shippingRecord(String role){
        if (ACL.hasAccess(role,"Sell")){
            shippingClient.shippingRecord();
            return shippingClient.getMessages();
        }

            return "This role has no access";
    }

    public AccessControlList getACL() {
        return ACL;
    }

    public void setACL(AccessControlList ACL) {
        this.ACL = ACL;
    }

    public InventoryClient getInventoryClient() {
        return inventoryClient;
    }

    public void setInventoryClient(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    public ShippingClient getShippingClient() {
        return shippingClient;
    }

    public void setShippingClient(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

}