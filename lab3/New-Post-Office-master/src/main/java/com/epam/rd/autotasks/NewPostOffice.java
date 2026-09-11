package com.epam.rd.autotasks;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NewPostOffice {
    private final Collection<Box> listBox;
    private static final int COST_KILOGRAM = 5;
    private static final int COST_CUBIC_METER = 100;
    private static final double COEFFICIENT = 0.5;

    public NewPostOffice() {
        listBox = new ArrayList<>();
    }

    public Collection<Box> getListBox() {
        return (Collection<Box>) ((ArrayList<Box>) listBox).clone();
    }

    static BigDecimal calculateCostOfBox(double weight, double volume, int value) {
        BigDecimal costWeight = BigDecimal.valueOf(weight)
                .multiply(BigDecimal.valueOf(COST_KILOGRAM), MathContext.DECIMAL64);
        BigDecimal costVolume = BigDecimal.valueOf(volume)
                .multiply(BigDecimal.valueOf(COST_CUBIC_METER), MathContext.DECIMAL64);
        return costVolume.add(costWeight)
                .add(BigDecimal.valueOf(COEFFICIENT * value), MathContext.DECIMAL64);
    }

    // implements student
    public boolean addBox(String addresser, String recipient, double weight, double volume, int value) {
        if (addresser == null ||
            recipient == null ||
            addresser.isBlank() ||
            recipient.isBlank() ||
            weight < 0.5 || weight > 20 ||
            volume <= 0 || volume > 0.25 ||
            value <= 0)
            throw new IllegalArgumentException();
        Box box = new Box(addresser, recipient, weight, volume);
        box.setCost(calculateCostOfBox(weight, volume, value));
        return listBox.add(box);
    }

    // implements student
    public Collection<Box> deliveryBoxToRecipient(String recipient) {
        try {
            Collection<Box> res = new ArrayList<>();
            for (Iterator<Box> iter = listBox.iterator(); iter.hasNext(); ) {
                Box tmp = iter.next();
                if (tmp.getRecipient().equals(recipient)) {
                    res.add(tmp.clone());
                    iter.remove();
                }
            }
            return res;
        }
        catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }

    public void declineCostOfBox(double percent) {
        BigDecimal bd = new BigDecimal(Double.toString(percent));
        BigDecimal multiplier = bd.negate()
                .add(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(100));

        for (Box box : listBox) {
            box.setCost(
                    box.getCost()
                            .multiply(multiplier, MathContext.DECIMAL64));
        }
    }

}
