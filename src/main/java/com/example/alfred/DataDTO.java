package com.example.alfred;
import com.sun.istack.internal.NotNull;
import java.io.Serializable;


public class DataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
