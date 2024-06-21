package DTOs;

public class PaymentDTO {
    private String title;
    private Float unitPrice;
    private Integer quantity;

    public PaymentDTO(String requestBody) {

    }

    public String getTitle() {
        return title;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
