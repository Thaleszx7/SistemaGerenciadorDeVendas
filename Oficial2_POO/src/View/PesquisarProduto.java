package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import SistemaDeVendas.Conexao;

public class PesquisarProduto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblNomeProduto;
    private JTable table;

    
    public PesquisarProduto() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(430, 50, 100, 30);
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pesquisarProduto();
            }
        });
        contentPane.add(btnPesquisar);

        textField = new JTextField();
        textField.setBounds(10, 50, 400, 30);
        contentPane.add(textField);

        lblNomeProduto = new JLabel("Nome Produto");
        lblNomeProduto.setForeground(SystemColor.text);
        lblNomeProduto.setBounds(10, 20, 123, 14);
        contentPane.add(lblNomeProduto);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 100, 580, 250);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Descrição");
        table.setModel(model);
    }

    // Método para pesquisar o produto
    private void pesquisarProduto() {
        String input = textField.getText();
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT id, descricao FROM produtos WHERE descricao LIKE ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + input + "%");

            rs = stmt.executeQuery();

            // Limpa os dados existentes na tabela
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            // Preenche a tabela com os resultados da consulta
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("descricao")});
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao pesquisar produto: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão: " + ex.getMessage());
            }
        }
    }

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PesquisarProduto frame = new PesquisarProduto();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
