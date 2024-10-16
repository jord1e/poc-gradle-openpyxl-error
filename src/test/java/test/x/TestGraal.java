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
            byte[] excelOutput = ctx.getBindings("python").getMember("output").as(byte[].class);
            Files.write(Path.of("test.xlsx"), excelOutput);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
