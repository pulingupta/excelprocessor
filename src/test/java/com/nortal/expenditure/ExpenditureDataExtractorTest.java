package com.nortal.expenditure;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.nortal.expenditure.ExpenditureDataExtractor;
import com.nortal.expenditure.model.Expenditure;

public class ExpenditureDataExtractorTest {

    @Test
    public void testDataExtractionWithSimpleData() throws Exception {
        // TODO improve test
        Expenditure expectedExpenditure = new Expenditure();
        expectedExpenditure.setDate(DateUtils.parseDate("14.02.2016","dd.MM.yyyy"));
        expectedExpenditure.setSupplier("Conemedia");
        expectedExpenditure.setType("Utilities");

        ExpenditureDataExtractor extractor = new ExpenditureDataExtractor();
        InputStream resource = ExpenditureDataExtractorTest.class.getResourceAsStream("/simple-expenditures.xlsx");
        List<Expenditure> expenditures = extractor.readFromFile(resource);

        assertThat(expenditures, contains(expectedExpenditure));
    }
}
