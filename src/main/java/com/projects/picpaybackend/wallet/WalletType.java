package com.projects.picpaybackend.wallet;

public enum WalletType {
    COMMON(1), SHOPKEEPER(2);

    private final int value;

    private WalletType(int value) {
	this.value = value;
    }

    public int getValue() {
	return value;
    }
}
