package com.minibank.model;

import java.io.Serializable;

public record BankInfo(String name, String branch) implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "========================================================\n"
             + "        " + name.toUpperCase() + " - " + branch.toUpperCase() + " BRANCH\n"
             + "   Next-Gen Secure Object-Oriented Banking Application   \n"
             + "========================================================";
    }
}
