package co.com.devsu.bank.account.utility;

import org.owasp.encoder.Encode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonsUtility {

    private static final Pattern PATTERN_ONLY_NUMBER = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static final Pattern PATTERN_PHONE_MOBILE_NUMBER = Pattern.compile("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d");

    public static final String SLASH = "/";
    public static final String COLON_AND_DOUBLE_SLASH = "://";


    private static CommonsUtility commonsUtility;

    public static CommonsUtility getInstance() {
        return Optional.ofNullable(commonsUtility).orElse(new CommonsUtility());
    }

    public Date convertStringYYYYMMDDtoDate(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(str);
        } catch (Exception exception) {
            return Calendar.getInstance().getTime();
        }
    }

    public Boolean validatePhone(String phone) {
        if (phone == null)
            return false;
        return isNumeric(phone) || PATTERN_PHONE_MOBILE_NUMBER.matcher(phone).matches();
    }

    private boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }
        return PATTERN_ONLY_NUMBER.matcher(string).matches();
    }

    public boolean isSanitized(final String string) {
        if (string == null)
            return false;
        final String sanitizedHTML = Encode.forHtml(string);
        return sanitizedHTML.equals(string);
    }

    public String generateRandomCode(Integer length) {
        Random random = new Random();
        return random.ints(length, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    public boolean validateEmail(String email) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public Date addToDate(Date date, Integer calendarType, Long measure) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarType, measure.intValue());
        return calendar.getTime();
    }

    public String getMaskPhone(String phone) {
        final int lastDigits = 4;
        final int maskedLength = Math.max(phone.length() - lastDigits, 0);
        String maskedDigits = IntStream.range(0, maskedLength)
                .mapToObj(i -> "*")
                .collect(Collectors.joining());
        String lastFourDigits = phone.substring(phone.length() - lastDigits);
        return maskedDigits + lastFourDigits;
    }
}
