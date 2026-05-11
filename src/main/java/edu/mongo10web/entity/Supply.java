package edu.mongo10web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supply {
    @Id
    private String id;
    private Product product;
    private Integer quantity;
    private SupplyStatus status;
    private LocalDateTime arriveDateTime;
    private LocalDateTime sendDateTime;

    public Supply(Product product, Integer quantity, SupplyStatus status, LocalDateTime arriveDateTime, LocalDateTime sendDateTime) {
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.arriveDateTime = arriveDateTime;
        this.sendDateTime = sendDateTime;
    }
}
