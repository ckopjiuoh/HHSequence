package com.company;

import java.math.BigInteger;

/**
 * Класс, в котором находится подпоследовательность
 * с методами поиска числа, индекс которого нужно найти.
 * Создан для лучшего понимания действия алгоритма.
 */
class Subsequence {

    private String subsequence;
    private boolean zeroesFlag = false;
    private String digit = ""; // Число индекс которого нужно найти
    private int indexOffset = 0; // индекс смещения(если число не полностью входит в подпоследовательность)
    private BigInteger index;

    Subsequence(String subsequence) {
        this.subsequence = subsequence;
        digit = check(subsequence);
        indexOffset = getIndexOffset(digit,subsequence);
        this.index = getIndex(digit,indexOffset);
    }

    public String getSubsequence() {
        return subsequence;
    }

    String getDigit() {
        return digit;
    }

    BigInteger getIndex() {
        return index;
    }

    private String check(String subSequence) {
        BigInteger subSeq = new BigInteger(subSequence);
        if (subSeq.equals(BigInteger.ZERO)) {
            indexOffset = 1;
            return "1" + subSequence;
        }
        if (subSequence.charAt(0) == '0') {
            return checkZeroDig(subSequence);
        }

        int length = subSequence.length();
        String searchDigit = "";
        int searchDigitStartInd;
        int searchDigitEndInd; // индексы искомго в подпоследовательности
        // Раскладываем подпоследовательность на все возможные числа(123 как 1, 2, 3, 12, 13, 123)
        for (int i = 1; i <= length; i++) { // i - длина числа
            int j = 0; // j - индекс числа в подпоследовательности
            while (i + j <= length) {
                searchDigit = subSequence.substring(j, i + j); // searchDigit является кандидатом на искомое числом
                if (searchDigit.charAt(0) == '0') { // Не рассматриваем числа начинающиеся с 0
                    if (j >= i - 1) { // В данном случае не имеет смысла рассматривать дальнейшие сравнения
                        searchDigit = "-1";
                        break;
                    }
                    searchDigit = "-1";
                    j++;
                    continue;
                }
                searchDigitStartInd = j; // Индексы начала и конца искомого числа
                searchDigitEndInd = i + j;
                //Задаем новые переменные для проверки следующих чисел в подпослдеовательности
                String nextDigit = searchDigit;
                int nextStartInd = searchDigitStartInd;
                int nextEndInd = searchDigitEndInd;
                //Пока есть следующие числа в подпоследовательности длинной <= searchDigit сравниваем их
                // если каждое следующее число на единицу больше текущего - все хорошо.
                // если нет - число выбрано неудачно, переходим к следующему.
                while (hasNext(subSequence, nextEndInd)) {
                    nextDigit = nextDig(subSequence, nextEndInd, nextDigit);
                    nextStartInd += i + j;
                    nextEndInd += i + j;
                    if (nextDigit.equals("-1")) {
                        searchDigit = "-1";
                        break;
                    }
                }
                if (j >= i - 1 && searchDigit.equals("-1")) { //Если число не подошло, и его индекс больше длины этого числа, дальнейшие сравнения не имеют смысла
                    break;
                }
                /* Если есть числа в подпоследовательности перед нашим кандидатом(searchDigit)
                 * значит нужно проверить, подойдет ли оно нам.
                 * На примере -
                 * subSequense : 2113114
                 * В данный момент наш кандидат - 113
                 * Предыдущим шагом мы проверили, что 114-113 = 1, значит оно удовлетворяет условию
                 * Но в начале стоит 2. Метод сверяет 2 и последнее число в 113 (3), если они удовлетворяют условию поиска,
                 * возвращает нового кандидата (112).
                 */
                if (!searchDigit.equals("-1") && hasPrev(searchDigitStartInd)) {
                    searchDigit = previousDig(subSequence, searchDigitStartInd, searchDigit);
                }
                if (!searchDigit.equals("-1")) { // Если после всех проверок, ни один из методов не вернул "-1", значит кандидат нам подходит.
                    break;
                }
                j++;
            }
            if (!searchDigit.equals("-1")) {
                if(searchDigit.length() == subSequence.length()){
                    if(!zeroesFlag) {
                        for (int m = 0; m <= subSequence.length(); m++) {
                            String newSeq = subSequence.charAt(subSequence.length() - 1) + subSequence.substring(0, subSequence.length() - 1);
                            if (newSeq.charAt(0) != '0') {
                                BigInteger newSeqInt = new BigInteger(newSeq);
                                subSeq = new BigInteger(subSequence);
                                if (newSeqInt.compareTo(subSeq) < 0) {
                                    searchDigit = newSeq;
                                }
                            }
                        }
                    }
                }
                return searchDigit;
            }
        }
        return searchDigit;
    }
    // Метод, проверяющий следующее число в подпоследовательности
    private String nextDig(String testStr, int endInd, String candidate) {
        BigInteger nextAftCand = new BigInteger(candidate).add(BigInteger.ONE);
        String nextDigit = String.valueOf(nextAftCand);
        String newCandidate;

        if (testStr.length() - endInd >= candidate.length()) {
            newCandidate = testStr.substring(endInd, endInd + candidate.length());
            if(newCandidate.equals(nextDigit)){
                return newCandidate;
            }
        } else {
            newCandidate = testStr.substring(endInd, testStr.length());
            if(newCandidate.equals(nextDigit.substring(0,newCandidate.length()))){
                return newCandidate;
            }
        }
        return "-1";
    }

    // Возвращает первое число подпоследовательности, если оно было неполным.
    private static String previousDig(String testStr, int startInd, String candidate) {
        BigInteger previous = new BigInteger(candidate).subtract(BigInteger.ONE);
        String newPreviousDig = String.valueOf(previous);
        String previousDigit;
        try {
            previousDigit = testStr.substring(0, startInd);
            if (newPreviousDig.substring(newPreviousDig.length() - previousDigit.length(), newPreviousDig.length()).equals(previousDigit)) {
                return newPreviousDig;
            }
        }catch (Exception e){
            return "-1";
        }
        return "-1";
    }

    // Есть ли следующее число в подпоследовательности
    private static boolean hasNext(String testStr, int nextEndInd) {

        return nextEndInd < testStr.length();
    }

    // Есть ли предыдущее число в подпоследовательности
    private static boolean hasPrev(int previousStartInd) {

        return previousStartInd >= 1;
    }

    /* Особый случай. Если в начале числа есть нули
    *  Проверяем, достраивая подпоследовательность.
    */
    private String checkZeroDig(String subSeq) {
        String zeroSCount = "";
        zeroesFlag = true;
        while (subSeq.charAt(0) == '0') {
            zeroSCount += "0";
            subSeq = subSeq.substring(1, subSeq.length());
        }
        String newCandidate = check(subSeq);
        BigInteger prevNewCand = new BigInteger(newCandidate).subtract(BigInteger.ONE);
        String prevCand = String.valueOf(prevNewCand);
        String prevCandZeroes = "";
        try {
            prevCandZeroes = prevCand.substring(prevCand.length() - zeroSCount.length(), prevCand.length());
        } catch (Exception e) {
            prevCandZeroes = "-1";
        }

        if(prevCandZeroes.equals(zeroSCount)){
            return prevCand;
        } else{
            String zeroECount = "";
            while (newCandidate.charAt(newCandidate.length()-1) == '0') {
                zeroECount += "0";
                newCandidate = newCandidate.substring(0, newCandidate.length()-1);
            }if(zeroECount.length() > zeroSCount.length()) {
                newCandidate = newCandidate + zeroECount+"0";
            } else if(zeroECount.length() == zeroSCount.length()) {
                newCandidate+=zeroECount+"0";
            }
            else { newCandidate = newCandidate + zeroSCount;}
        }
        return newCandidate;
    }
    // Метод нахождения индекса смещения
    private int getIndexOffset(String digit, String subsequence){
        int index = 0;
        if(digit.equals(subsequence)){
            return index;
        }

        for(int i = subsequence.length()-1; i>=0; i--){
            if(digit.charAt(digit.length()-1) == subsequence.charAt(i)){
                index = i+1;
                for(int j = digit.length()-1; j >=0; j--){
                    if(digit.charAt(j) == subsequence.charAt(i)) {
                        i--;
                        if(i <= 0){

                            index = digit.length() - index;
                            if(index<0){
                                return 0;
                            }
                            return index;
                        }
                    }else {
                        index = 0;
                        break;
                    }
                }
            }
        }
        return index;
    }
    // Получаем индекс числа
    private BigInteger getIndex(String digit, int indexOffset){

        BigInteger start = new BigInteger("1");
        BigInteger step = new BigInteger("1");
        BigInteger length = new BigInteger("9");
        BigInteger digitVal = new BigInteger(digit);

        while(length.compareTo(digitVal) < 0){
            digitVal = digitVal.subtract(length);
            start = start.add(step.multiply(length));
            step = step.add(BigInteger.ONE);
            length = length.multiply(BigInteger.TEN);
        }
        BigInteger index = start.add((digitVal.subtract(BigInteger.ONE)).multiply(step)).add(BigInteger.valueOf(indexOffset));
        return index;
    }

    public int getIndexOffset() {
        return indexOffset;
    }
}
