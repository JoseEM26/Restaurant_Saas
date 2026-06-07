package com.restaurant.reportes.controller;

import com.restaurant.reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Generación de reportes PDF con JasperReports")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping(value = "/ventas/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Reporte de ventas en PDF")
    public ResponseEntity<byte[]> reporteVentasPdf(
            @RequestParam(defaultValue = "2024") String anio,
            @RequestParam(defaultValue = "01") String mes) {

        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "Reporte de Ventas");
        params.put("PERIODO", mes + "/" + anio);

        // En un caso real, aquí se consultan datos via Feign a ms-ventas
        byte[] pdf = reporteService.generarPdfConListaVacia("reporte-ventas", params);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ventas-" + mes + "-" + anio + ".pdf")
                .body(pdf);
    }

    @GetMapping(value = "/pedidos/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Reporte de pedidos en PDF")
    public ResponseEntity<byte[]> reportePedidosPdf(@RequestParam(defaultValue = "today") String fecha) {

        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "Reporte de Pedidos");
        params.put("FECHA", fecha);

        byte[] pdf = reporteService.generarPdfConListaVacia("reporte-pedidos", params);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=pedidos-" + fecha + ".pdf")
                .body(pdf);
    }
}
