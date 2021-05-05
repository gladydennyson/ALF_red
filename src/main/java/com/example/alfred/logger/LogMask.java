package com.example.alfred.logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

/**
 * A class to mask confidential information like SSN, CVV, Credit card number
 *
 */
@Plugin(name="LogMaskingConverter", category = "Converter")
@ConverterKeys({"spi","trscId"})
public class LogMask extends LogEventPatternConverter{
	
	// matcher for credit card
    private static final String CREDIT_CARD_REGEX = "\"[Cc][Rr][Ee][Dd][Ii][Tt][Cc][Aa][Rr][Dd]\"\\s*:\\s*([0-9]{16})";;
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_REGEX);
    private static final String CAREDIT_CARD_REPLACEMENT_REGEX = "\"CREDITCARD\" : \"XXXXXXXXXXXXXXXX\"";

    // matcher for cvv
    private static final String CVV_REGEX = "(\"[Cc][Vv]{2}\"\\s*:\\s*[0-9]{3})";
    private static final Pattern CVV_PATTERN = Pattern.compile(CVV_REGEX);
    private static final String CVV_REPLACEMENT_REGEX = "\"cvv\" : \"+++\"";

    // matcher for ssn
    private static final String SSN_REGEX = "(\"[Ss]{2}[Nn]\"\\s*:\\s*[0-9]{9})";
    private static final Pattern SSN_PATTERN = Pattern.compile(SSN_REGEX);
    private static final String SSN_REPLACEMENT_REGEX = "\"SSN\" : \"***\"";

    
    /**
     * 
     * Constructor
     * @param name
     * @param style
     */
    protected LogMask(String name, String style) {
        super(name, style);
    }

    /**
     * gets the "spi" from the log4j properties to mask the data
     * @param options
     * @return
     */
    public static LogMask newInstance(String[] options) {
        return new LogMask("spi",Thread.currentThread().getName());
    }

    
    /**
     * Does the masking for the matcher defined
     */
    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;
        try {
            maskedMessage = mask(message);
        } catch (Exception e) {
            System.out.println("Failed While Masking");
            maskedMessage = message;
        }
        outputMessage.append(maskedMessage);

    }

    /**
     * masks the logs
     * @param message
     * @return
     */
    private String mask(String message) {
        Matcher matcher =null;
        StringBuffer buffer = new StringBuffer();

        // masks the credit card number
        matcher = CREDIT_CARD_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,CAREDIT_CARD_REPLACEMENT_REGEX);
        message=buffer.toString();
        buffer.setLength(0);

        //masks the ssn
        matcher = SSN_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,SSN_REPLACEMENT_REGEX);
        message=buffer.toString();
        buffer.setLength(0);

        // masks the cvv
        matcher = CVV_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,CVV_REPLACEMENT_REGEX);

        return buffer.toString();
    }

    /**
     * 
     * matches the defined matchers and replaces the strings
     * @param matcher
     * @param buffer
     * @param maskStr
     * @return
     */
    private StringBuffer maskMatcher(Matcher matcher, StringBuffer buffer, String maskStr)
    {
    	// matches and replaces
        while (matcher.find()) {
            matcher.appendReplacement(buffer,maskStr);
        }
        matcher.appendTail(buffer);
        return buffer;
    }

}