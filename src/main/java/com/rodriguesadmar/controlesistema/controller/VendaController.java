package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import com.rodriguesadmar.controlesistema.repository.PedidoRepository;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import com.rodriguesadmar.controlesistema.service.ClienteService;
import com.rodriguesadmar.controlesistema.service.ProdutoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.*;
import org.openpdf.text.pdf.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;


    @GetMapping("/nova")
    public String novaVenda(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("produtos", produtoService.findAll());
        return "vendas/formulario.html";
    }

    @GetMapping
    public String pedidos(Model model) {
        List<PedidoVenda> vendas = pedidoRepository.findAllByOrderByDataHoraDesc();
        model.addAttribute("vendas", vendas);
        return "vendas/lista";

    }

    @GetMapping("/{id}")
    public String detalhesVenda(@PathVariable Long id, Model model) {
        PedidoVenda pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda invalida: " + id));

        model.addAttribute("pedido", pedido);
        return "vendas/detalhes";

    }

    @GetMapping("/{id}/comprovante")
    public void comprovanteVenda(@PathVariable Long id, HttpServletResponse response) {

        PedidoVenda pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Venda Invalida: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=comprovante.pdf");

        ;
        try (Document documento = new Document()){
            PdfWriter.getInstance(documento, response.getOutputStream());
            documento.open();
            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fonteNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fonteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            NumberFormat moeda = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));


            Paragraph titulo = new Paragraph("COMPROVANTE DE VENDA", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            documento.add(titulo);

            PdfPTable tabelaDados = new PdfPTable(2);
            tabelaDados.setWidthPercentage(100);
            tabelaDados.setSpacingAfter(20);

            PdfPCell celulaCliente = new PdfPCell();
            celulaCliente.setBorder(Rectangle.NO_BORDER);
            celulaCliente.addElement(new Paragraph("Pedido #: " + pedido.getId(), fonteNegrito));
            celulaCliente.addElement(new Paragraph("Cliente: " + pedido.getCliente().getNome(), fonteNormal));
            tabelaDados.addCell(celulaCliente);

            PdfPCell celulaData = new PdfPCell();
            celulaData.setBorder(Rectangle.NO_BORDER);
            celulaData.setHorizontalAlignment(Element.ALIGN_RIGHT);
            String dataFormatada = pedido.getDataHora().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            celulaData.addElement(new Paragraph("Data: " + dataFormatada, fonteNormal));
            tabelaDados.addCell(celulaData);

            documento.add(tabelaDados);

            float[] largurasColunas = {40f, 15f, 20f, 25f};
            PdfPTable tabelaItens = new PdfPTable(largurasColunas);
            tabelaItens.setWidthPercentage(100);

            String[] headers = {"Produto", "Qtd", "Preço Unit.", "Total"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fonteNegrito));
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                tabelaItens.addCell(cell);
            }

            for (var item : pedido.getItens()) {
                tabelaItens.addCell(new Phrase(item.getProduto().getNome(), fonteNormal));

                PdfPCell cellQtd = new PdfPCell(new Phrase(item.getQuantitdade().toString(), fonteNormal));
                cellQtd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaItens.addCell(cellQtd);

                PdfPCell cellPreco = new PdfPCell(new Phrase(moeda.format(item.getPrecoUnitario()), fonteNormal));
                cellPreco.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabelaItens.addCell(cellPreco);

                PdfPCell cellSub = new PdfPCell(new Phrase(moeda.format(item.getSubTotal()), fonteNormal));
                cellSub.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabelaItens.addCell(cellSub);
            }

            documento.add(tabelaItens);

            Paragraph total = new Paragraph("\nTOTAL A PAGAR: " + moeda.format(pedido.getValorTotal()), fonteTitulo);
            total.setAlignment(Element.ALIGN_RIGHT);
            documento.add(total);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }










    }


}
