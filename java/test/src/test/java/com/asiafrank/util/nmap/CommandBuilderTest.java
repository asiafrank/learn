package com.asiafrank.util.nmap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * CommandBuilderTest
 * <p>
 * Created by asiafrank on 9/12/2016.
 */
public class CommandBuilderTest {

    @Test
    public void buildCommand() {
        CommandBuilder cmd = CommandBuilder.newInstance("");
        cmd.addOptions(Options.O, Options.oX());
        cmd.addTargets("10.1.11.235", "10.1.240.79");
        assertEquals("nmap -O -oX - 10.1.11.235 10.1.240.79 --stats-every 1", cmd.build());
    }
}
