package net.kore.pronouns.common;

import net.kore.pronouns.common.command.DataType;

public enum PronounNumber {
    pronoun1(DataType.PRONOUN1),
    pronoun2(DataType.PRONOUN2),
    pronoun3(DataType.PRONOUN3);

    private final DataType dataType;
    public DataType getDataType() {
        return dataType;
    }
    PronounNumber(DataType dataType) {
        this.dataType = dataType;
    }
}
