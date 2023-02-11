package monoalphabeticCipher.src.monoalphabeticCipher.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Driver {

    private static final int MIN_SEED = 50;
    private static final int MAX_SEED = 10000;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM  = ALPHABET + DIGITS;
    private Map<Character, Character> encryptionMap;
    private Map<Character, Character> decryptionMap;

    public Driver(int seed) {
        encryptionMap = new HashMap<>();
        decryptionMap = new HashMap<>();
        generateMapping(seed);
    }

    private void generateMapping(int seed) {
        Random rand = new Random(seed);
        List<Character> alphanumList = new ArrayList<>(ALPHANUM.length());
        for (char c : ALPHANUM.toCharArray()) {
            alphanumList.add(c);
        }
        Collections.shuffle(alphanumList, rand);
        for (int i = 0; i < ALPHANUM.length(); i++) {
            encryptionMap.put(ALPHANUM.charAt(i), alphanumList.get(i));
            decryptionMap.put(alphanumList.get(i), ALPHANUM.charAt(i));
        }
    }

    private String encrypt(String plaintext) {
        StringBuilder sb = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            sb.append(encryptionMap.get(c));
        }
        return sb.toString();
    }

    private String decrypt(String ciphertext) {
        StringBuilder sb = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            sb.append(decryptionMap.get(c));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java Mono <inputfile> <outputfile> <seed> 1/0");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
        int seed = Integer.parseInt(args[2]);
        int action = Integer.parseInt(args[3]);
        Driver mono = new Driver(seed);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (action == 1) {
                    line = mono.encrypt(line);
                } else if (action == 0) {
                    line = mono.decrypt(line);
                }
                writer.write(line);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }
        System.out.print("Mapping: ");
    }
}
