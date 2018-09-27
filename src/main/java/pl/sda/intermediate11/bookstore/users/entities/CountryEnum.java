package pl.sda.intermediate11.bookstore.users.entities;

import lombok.Getter;

@Getter
public enum CountryEnum {
    POLAND("PL","Polska"), GERMANY("DE","Niemcy"), ENGLAND("ENG","Anglia"), RUSSIA("RUS","Rosja"), FRANCE("FR","Francja");

    private String symbol;
    private String plName;

    CountryEnum(String symbol, String plName) {
        this.symbol = symbol;
        this.plName = plName;
    }
}
