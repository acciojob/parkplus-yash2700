package com.driver.model;

import javax.persistence.Table;

@Table(name = "paymentmode")
public enum PaymentMode {

    CASH, CARD, UPI
}