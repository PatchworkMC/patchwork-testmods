package com.patchworkmc.testmods.annotation_scanning;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patchwork-test-annotation-scanning")
public class AnnotationScanningTest {
    @CustomAnnotation(arg1 = 3)
    public static class AnnotatedClass {
        @CustomAnnotation(arg2 = "a")
        public static int field = 1;

        @CustomAnnotation(arg2 = "b")
        public static void method1(int methodArg) {

        }

        @CustomAnnotation(arg2 = "c")
        public static String method2(String methodArg) {
            return null;
        }
    }

    private static Logger logger = LogManager.getLogger("patchwork-test-annotation-scanning");

    public AnnotationScanningTest() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        ModFileScanData scanData = ModList.get()
                .getModFileById("patchwork-test-annotation-scanning").getFile().getScanResult();
        scanData.getAnnotations().forEach(annotationData -> {
            logger.info(String.format(
                    "Found Annotation\n%s\n%s\n%s\n%s\n%s",
                    annotationData.getTargetType(),
                    annotationData.getAnnotationType(),
                    annotationData.getClassType(),
                    annotationData.getMemberName(),
                    annotationData.getAnnotationData()
            ));
        });
    }
}
