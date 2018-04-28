package com.nortal.expenditure;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.nortal.expenditure.model.Expenditure;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.sl.usermodel.Line;

public class ExpenditureStatisticsCalculator {

    public ExpenditureStatistics calcExpenditureStatistics(InputStream expenditureStream) {
        List<Expenditure> exp = new ExpenditureDataExtractor().readFromFile(expenditureStream);
        // TODO needs more implementation
        exp.forEach(u -> {
            u.setTotalExpense(getTotalExpenseAndFees(u).getTotalExpense());
            u.setTotalExpenseWithFee(getTotalExpenseAndFees(u).getTotalExpenseWithFee());
        });
        Double overallExpenses = exp.stream().mapToDouble(o-> {
            return o.getTotalExpense();
        }).sum();

        Double overallExpensesWithFees = exp.stream().mapToDouble(o->{
            return o.getTotalExpenseWithFee();
        }).sum();
        ExpenditureStatistics expStats = new ExpenditureStatistics();
        expStats.setAvgFee(getAvgFee(overallExpensesWithFees,overallExpenses));
        return expStats;
    }



    private Expenditure getTotalExpenseAndFees(Expenditure exp) {
        exp.setTotalExpense(exp.getUnits() * exp.getUnitPrice());
        exp.setTotalExpenseWithFee(getMaxFeesGained(exp.getTotalExpense(), exp.getTaxPercentage()));
        return exp;
    }

    private double getMaxFeesGained(Double totalAmount, Double taxPercentage) {
        return  Math.floor(totalAmount * taxPercentage) / 100;
    }

    private BigDecimal getAvgFee(Double overallExpensesWithFees, Double overallExpenses){
        return new BigDecimal(Math.floor(overallExpensesWithFees * 100)/ overallExpenses, MathContext.DECIMAL64);
    }

    public static class ExpenditureStatistics {
        private String maxFeesGainedSupplier;
        private BigDecimal avgFee;
        private Pair<String, String> smallestFeeDifferenceProducts;

        /**
         * @return Name of the supplier who has gained most money in additional fees
         */
        public String getMaxFeesGainedSupplier() {
            return maxFeesGainedSupplier;
        }

        public void setMaxFeesGainedSupplier(String maxFeesGainedSupplier) {
            this.maxFeesGainedSupplier = maxFeesGainedSupplier;
        }

        /**
         * @return Average fee percentage in decimal form: 34.5% = 0.345
         */
        public BigDecimal getAvgFee() {
            return avgFee;
        }

        public void setAvgFee(BigDecimal avgFee) {
            this.avgFee = avgFee;
        }

        /**
         * @return Pair of product names that have smallest difference in total paid fees
         */
        public Pair<String, String> getSmallestFeeDifferenceProducts() {
            return smallestFeeDifferenceProducts;
        }

        public void setSmallestFeeDifferenceProducts(Pair<String, String> smallestFeeDifferenceProducts) {
            this.smallestFeeDifferenceProducts = smallestFeeDifferenceProducts;
        }
    }
}
