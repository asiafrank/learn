package com.asiafrank.jarckeck;

import com.asiafrank.jarcheck.ConflictClazz;
import com.asiafrank.jarcheck.ConflictJar;
import com.asiafrank.jarcheck.JarConflictDetector;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * @author zhangxf created at 8/6/2018.
 */
public class JarConflictDetectorTest {

    @Test
    public void test() {
        try {
            JarConflictDetector detector = new JarConflictDetector("<jar lib dir>");
            detector.start();
            List<ConflictJar> conflictJars = detector.getConflictJars();
            List<ConflictClazz> conflictClazzes = detector.getConflictClazzes();

            for (ConflictJar cj : conflictJars) {
                System.out.println(cj);
            }

            System.out.println("=====================");

            for (ConflictClazz cc : conflictClazzes) {
                System.out.println(cc);
            }
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
    }
}
