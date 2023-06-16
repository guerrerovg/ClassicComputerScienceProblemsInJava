package chapter1;

import java.util.BitSet;

public class CompressedGene {

    private BitSet bitSet;
    private int length;

    public CompressedGene(String gene) {
        compress(gene);
    }

    public void compress(String gene) {
        length = gene.length();
        bitSet = new BitSet(length * 2);

        final String upperGene = gene.toUpperCase();

        for (int i = 0; i < length; i++) {
            final int firstLocation = 2 * i;
            final int secondLocation = 2 * i + 1;

            switch (upperGene.charAt(i)) {
                case 'A': // 00
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, false);
                    break;

                case 'C': // 01
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, true);
                    break;

                case 'G': // 10
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, false);
                    break;

                case 'T': // 11
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, true);
                    break;

                default:
                    throw new IllegalArgumentException("The provided gene String " +
                            "contains characters other than ACGT");
            }
        }
    }

    public String decompress() {
        if (bitSet == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length * 2; i += 2) {
            final int firstBit = (bitSet.get(i) ? 1 : 0);
            final int secondBit = (bitSet.get(i + 1) ? 1 : 0);

            final int lastBits = firstBit << 1 | secondBit;

            switch (lastBits) {
                case 0b00: // 00 is A
                    builder.append("A");
                    break;

                case 0b01: // 01 is C
                    builder.append("C");
                    break;

                case 0b10: // 10 is G
                    builder.append("G");
                    break;

                case 0b11: // 11 is T
                    builder.append("T");
                    break;
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        String original = "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGA" +
                "TTAACCGTTATATATATATAGCCATGGATCGATTATA";

        CompressedGene gene = new CompressedGene(original);

        String decompressed = gene.decompress();

        System.out.println(original);
        System.out.println(decompressed);
        System.out.println("Original is the same as decompressed: " + original.equalsIgnoreCase(decompressed));
    }
}
