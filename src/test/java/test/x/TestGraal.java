package test.x;

import org.graalvm.python.embedding.utils.GraalPyResources;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class TestGraal {

    @Test
    void testGraalPyOpenXl() {
        try (var ctx = GraalPyResources.createContext()) {
            ctx.eval("python", """
                    import openpyxl
                    import io
                    
                    wb = openpyxl.Workbook()
                    wb.active['A1'] = 'Hello world!'
                    
                    out_bytes = io.BytesIO()
                    wb.save(out_bytes)
                    output = out_bytes.getvalue()
                    """);

            int[] excelOutput = ctx.getBindings("python").getMember("output").as(int[].class);
            byte[] excelOutputSigned = new byte[excelOutput.length];
            for (int i = 0; i < excelOutput.length; i++) {
                excelOutputSigned[i] = (byte) excelOutput[i];
            }
            Files.write(Path.of("test.xlsx"), excelOutputSigned);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
