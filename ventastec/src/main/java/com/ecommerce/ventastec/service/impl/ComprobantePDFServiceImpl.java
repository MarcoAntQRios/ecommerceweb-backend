package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.dto.response.DetalleComprobanteDTO;
import com.ecommerce.ventastec.service.ComprobantePDFService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ComprobantePDFServiceImpl implements ComprobantePDFService {

    private static final Color COLOR_PRIMARIO    = new Color(30, 58, 138);
    private static final Color COLOR_SECUNDARIO  = new Color(239, 246, 255);
    private static final Color COLOR_ACENTO      = new Color(59, 130, 246);
    private static final Color COLOR_EXITO       = new Color(22, 163, 74);
    private static final Color COLOR_TEXTO       = new Color(30, 41, 59);
    private static final Color COLOR_TEXTO_CLARO = new Color(100, 116, 139);
    private static final Color COLOR_BORDE       = new Color(203, 213, 225);

    @Override
    public ByteArrayInputStream generarComprobante(
            Long ventaId,
            String cliente,
            Double total,
            String stripePaymentId,
            List<DetalleComprobanteDTO> detalles) {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            agregarEncabezado(document, ventaId);
            agregarInfoCliente(document, cliente);
            agregarDetallesPago(document, total, stripePaymentId, detalles);
            agregarPie(document);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando comprobante PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // ─── ENCABEZADO ───────────────────────────────────────────────────────────

    private void agregarEncabezado(Document doc, Long ventaId) throws DocumentException {
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{60f, 40f});
        header.setSpacingAfter(20f);

        // Celda izquierda: nombre empresa
        PdfPCell celdaEmpresa = new PdfPCell();
        celdaEmpresa.setBorder(Rectangle.NO_BORDER);
        celdaEmpresa.setPaddingBottom(10f);

        Font fuenteEmpresa = new Font(Font.HELVETICA, 22, Font.BOLD, COLOR_PRIMARIO);
        celdaEmpresa.addElement(new Paragraph("ProducTec", fuenteEmpresa));

        Font fuenteSlogan = new Font(Font.HELVETICA, 9, Font.NORMAL, COLOR_TEXTO_CLARO);
        celdaEmpresa.addElement(new Paragraph("Tu tienda tecnológica de confianza", fuenteSlogan));
        header.addCell(celdaEmpresa);

        // Celda derecha: número de comprobante
        PdfPCell celdaNumero = new PdfPCell();
        celdaNumero.setBackgroundColor(COLOR_PRIMARIO);
        celdaNumero.setBorder(Rectangle.NO_BORDER);
        celdaNumero.setPadding(12f);

        Font fuenteTitulo = new Font(Font.HELVETICA, 8, Font.BOLD, Color.WHITE);
        Paragraph titulo = new Paragraph("COMPROBANTE DE PAGO", fuenteTitulo);
        titulo.setAlignment(Element.ALIGN_RIGHT);
        celdaNumero.addElement(titulo);

        Font fuenteNumero = new Font(Font.HELVETICA, 18, Font.BOLD, Color.WHITE);
        Paragraph numero = new Paragraph("NRO ORD-" + ventaId, fuenteNumero);
        numero.setAlignment(Element.ALIGN_RIGHT);
        celdaNumero.addElement(numero);

        header.addCell(celdaNumero);
        doc.add(header);

        agregarLinea(doc, COLOR_ACENTO, 2f);
    }

    // ─── INFO CLIENTE Y FECHA ─────────────────────────────────────────────────

    private void agregarInfoCliente(Document doc, String cliente) throws DocumentException {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{50f, 50f});
        tabla.setSpacingBefore(20f);
        tabla.setSpacingAfter(20f);

        // Columna izquierda: cliente
        PdfPCell celdaCliente = new PdfPCell();
        celdaCliente.setBorder(Rectangle.NO_BORDER);

        Font labelFont = new Font(Font.HELVETICA, 8, Font.BOLD, COLOR_TEXTO_CLARO);
        Font valueFont = new Font(Font.HELVETICA, 11, Font.BOLD, COLOR_TEXTO);

        celdaCliente.addElement(new Paragraph("CLIENTE", labelFont));
        celdaCliente.addElement(new Paragraph(cliente, valueFont));
        tabla.addCell(celdaCliente);

        // Columna derecha: fecha (sin hora)
        PdfPCell celdaFecha = new PdfPCell();
        celdaFecha.setBorder(Rectangle.NO_BORDER);

        String fechaActual = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Font labelDerFont = new Font(Font.HELVETICA, 8, Font.BOLD, COLOR_TEXTO_CLARO);
        Font valueDerFont = new Font(Font.HELVETICA, 10, Font.NORMAL, COLOR_TEXTO);

        Paragraph pFechaLabel = new Paragraph("FECHA DE EMISION", labelDerFont);
        pFechaLabel.setAlignment(Element.ALIGN_RIGHT);
        celdaFecha.addElement(pFechaLabel);

        Paragraph pFechaVal = new Paragraph(fechaActual, valueDerFont);
        pFechaVal.setAlignment(Element.ALIGN_RIGHT);
        celdaFecha.addElement(pFechaVal);

        tabla.addCell(celdaFecha);
        doc.add(tabla);
    }

    // ─── DETALLES DEL PAGO ────────────────────────────────────────────────────

    private void agregarDetallesPago(Document doc, Double total,
                                     String stripePaymentId,
                                     List<DetalleComprobanteDTO> detalles)
            throws DocumentException {

        Font secFont = new Font(Font.HELVETICA, 10, Font.BOLD, COLOR_PRIMARIO);
        Paragraph secTitulo = new Paragraph("DETALLES DEL PAGO", secFont);
        secTitulo.setSpacingBefore(5f);
        secTitulo.setSpacingAfter(8f);
        doc.add(secTitulo);

        // 3 columnas: Producto | Cantidad | P. Unitario
        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{55f, 15f, 30f});
        tabla.setSpacingAfter(20f);

        agregarCeldaEncabezado(tabla, "PRODUCTO");
        agregarCeldaEncabezado(tabla, "CANT.");
        agregarCeldaEncabezado(tabla, "P. UNITARIO");

        boolean alternar = false;
        for (DetalleComprobanteDTO detalle : detalles) {
            Color bg = alternar ? COLOR_SECUNDARIO : Color.WHITE;

            agregarCeldaFila(tabla, detalle.getNombreProducto(), bg, Element.ALIGN_LEFT);
            agregarCeldaFila(tabla, String.valueOf(detalle.getCantidad()), bg, Element.ALIGN_CENTER);
            agregarCeldaFila(tabla, formatMonto(detalle.getPrecioUnitario()), bg, Element.ALIGN_RIGHT);

            alternar = !alternar;
        }

        agregarFilaVacia(tabla, 3);
        agregarFilaTotalFinal(tabla, "TOTAL A PAGAR", formatMonto(total));

        doc.add(tabla);
        agregarCajaPago(doc);
    }
    // ─── CELDAS Y FILAS ───────────────────────────────────────────────────────

    private void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        Font f = new Font(Font.HELVETICA, 9, Font.BOLD, Color.WHITE);
        PdfPCell celda = new PdfPCell(new Phrase(texto, f));
        celda.setBackgroundColor(COLOR_PRIMARIO);
        celda.setPadding(10f);
        celda.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(celda);
    }

    private void agregarCeldaFila(PdfPTable tabla, String texto, Color bg, int alineacion) {
        Font f = new Font(Font.HELVETICA, 9, Font.NORMAL, COLOR_TEXTO);
        PdfPCell celda = new PdfPCell(new Phrase(texto, f));
        celda.setPadding(8f);
        celda.setBackgroundColor(bg);
        celda.setBorderColor(COLOR_BORDE);
        celda.setBorderWidth(0.5f);
        celda.setHorizontalAlignment(alineacion);
        tabla.addCell(celda);
    }

    private void agregarFilaResumen(PdfPTable tabla, String label, String valor) {
        Font fLabel = new Font(Font.HELVETICA, 9, Font.NORMAL, COLOR_TEXTO_CLARO);
        Font fValor = new Font(Font.HELVETICA, 9, Font.NORMAL, COLOR_TEXTO);

        PdfPCell cLabel = new PdfPCell(new Phrase(label, fLabel));
        cLabel.setColspan(3);
        cLabel.setPadding(6f);
        cLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cLabel.setBorderColor(COLOR_BORDE);
        cLabel.setBorderWidth(0.5f);
        tabla.addCell(cLabel);

        PdfPCell cValor = new PdfPCell(new Phrase(valor, fValor));
        cValor.setPadding(6f);
        cValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cValor.setBorderColor(COLOR_BORDE);
        cValor.setBorderWidth(0.5f);
        tabla.addCell(cValor);
    }

    private void agregarFilaTotalFinal(PdfPTable tabla, String label, String monto) {
        Font f = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);

        PdfPCell c1 = new PdfPCell(new Phrase(label, f));
        c1.setColspan(2); // ← era 3, ahora 2
        c1.setPadding(12f);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBackgroundColor(COLOR_PRIMARIO);
        c1.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase(monto, f));
        c2.setPadding(12f);
        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c2.setBackgroundColor(COLOR_PRIMARIO);
        c2.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(c2);
    }
    private void agregarFilaVacia(PdfPTable tabla, int colspan) {
        PdfPCell c = new PdfPCell(new Phrase(" "));
        c.setColspan(colspan);
        c.setBorder(Rectangle.NO_BORDER);
        c.setFixedHeight(6f);
        tabla.addCell(c);
    }

    // ─── CAJA PAGO EXITOSO ────────────────────────────────────────────────────

    private void agregarCajaPago(Document doc) throws DocumentException {
        PdfPTable caja = new PdfPTable(1);
        caja.setWidthPercentage(100);
        caja.setSpacingAfter(20f);

        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(new Color(240, 253, 244));
        celda.setBorderColor(COLOR_EXITO);
        celda.setBorderWidth(1.5f);
        celda.setPadding(12f);

        Font estadoFont = new Font(Font.HELVETICA, 10, Font.BOLD, COLOR_EXITO);
        celda.addElement(new Paragraph("PAGO PROCESADO EXITOSAMENTE", estadoFont));

        caja.addCell(celda);
        doc.add(caja);
    }

    // ─── PIE DE PÁGINA ────────────────────────────────────────────────────────

    private void agregarPie(Document doc) throws DocumentException {
        agregarLinea(doc, COLOR_BORDE, 1f);

        Font pieFont = new Font(Font.HELVETICA, 8, Font.NORMAL, COLOR_TEXTO_CLARO);

        Paragraph gracias = new Paragraph("Gracias por tu compra!", pieFont);
        gracias.setAlignment(Element.ALIGN_CENTER);
        gracias.setSpacingBefore(12f);
        gracias.setSpacingAfter(4f);
        doc.add(gracias);

        Paragraph contacto = new Paragraph(
                "Para consultas: soporte@productec.com  |  www.productec.com", pieFont);
        contacto.setAlignment(Element.ALIGN_CENTER);
        doc.add(contacto);

        Font numFont = new Font(Font.HELVETICA, 7, Font.NORMAL, COLOR_BORDE);
        Paragraph pagina = new Paragraph("Pagina 1 de 1", numFont);
        pagina.setAlignment(Element.ALIGN_CENTER);
        pagina.setSpacingBefore(8f);
        doc.add(pagina);
    }

    // ─── UTILIDADES ──────────────────────────────────────────────────────────

    private void agregarLinea(Document doc, Color color, float grosor) throws DocumentException {
        PdfPTable linea = new PdfPTable(1);
        linea.setWidthPercentage(100);

        PdfPCell celda = new PdfPCell();
        celda.setBorder(Rectangle.BOTTOM);
        celda.setBorderColor(color);
        celda.setBorderWidth(grosor);
        celda.setFixedHeight(grosor + 4f);
        linea.addCell(celda);
        doc.add(linea);
    }

    private String formatMonto(double monto) {
        return String.format(java.util.Locale.US, "S/ %.2f", monto);
    }
}