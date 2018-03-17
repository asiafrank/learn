package com.springinaction.knights;

public class DamselRescuingKnight implements Knight {

    private RescueDamselQuest quest;

    public DamselRescuingKnight() {
        this.quest = new RescueDamselQuest(); // Tightly coupled to RescueDamselQuest
    }

    public void embarkOnQuest() {
        quest.embark();
    }
}
