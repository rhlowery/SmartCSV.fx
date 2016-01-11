/*
   The MIT License (MIT)
   -----------------------------------------------------------------------------

   Copyright (c) 2015 javafx.ninja <info@javafx.ninja>                                              
                                                                                                                    
   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in
   all copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
   THE SOFTWARE.
  
*/

package ninja.javafx.smartcsv.fx.preferences;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ninja.javafx.smartcsv.fx.FXMLController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.supercsv.prefs.CsvPreference;

import java.net.URL;
import java.util.ResourceBundle;

import static ninja.javafx.smartcsv.preferences.QuoteModeHelper.getQuoteMode;
import static ninja.javafx.smartcsv.preferences.QuoteModeHelper.getQuoteModeName;

/**
 * controller for preferences
 */
@Component
public class PreferencesController extends FXMLController {

    @FXML
    private TextField quoteChar;

    @FXML
    private TextField delimiterChar;

    @FXML
    private CheckBox surroundingSpacesNeedQuotes;

    @FXML
    private CheckBox ignoreEmptyLines;

    @FXML
    private ComboBox<String> quoteMode;

    private String endOfLineSymbols;


    @Value("${fxml.smartcvs.preferences.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quoteMode.getItems().addAll("normal", "always", "column");
    }

    public void setCsvPreference(CsvPreference csvPreference) {
        quoteChar.setText(Character.toString(csvPreference.getQuoteChar()));
        delimiterChar.setText(Character.toString((char)csvPreference.getDelimiterChar()));
        surroundingSpacesNeedQuotes.setSelected(csvPreference.isSurroundingSpacesNeedQuotes());
        ignoreEmptyLines.setSelected(csvPreference.isIgnoreEmptyLines());
        quoteMode.getSelectionModel().select(getQuoteModeName(csvPreference.getQuoteMode()));
        endOfLineSymbols = csvPreference.getEndOfLineSymbols();
    }

    public CsvPreference getCsvPreference() {
        return new CsvPreference.Builder(quoteChar.getText().charAt(0), delimiterChar.getText().charAt(0), endOfLineSymbols)
                .useQuoteMode(getQuoteMode(quoteMode.getSelectionModel().getSelectedItem()))
                .surroundingSpacesNeedQuotes(surroundingSpacesNeedQuotes.isSelected())
                .ignoreEmptyLines(ignoreEmptyLines.isSelected())
                .build();
    }
}
