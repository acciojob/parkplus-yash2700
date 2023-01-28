package com.driver.model;

import javax.persistence.Table;

@Table(name = "spottype")
public enum SpotType {

    TWO_WHEELER, FOUR_WHEELER, OTHERS
}
