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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import SistemaDeVendas.Conexao;

public class NovoProduto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textPreco;
    private JTextField textProduto;
    private JLabel lblId;
    private JLabel lblNomeProduto;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NovoProduto frame = new NovoProduto();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public NovoProduto() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 478, 200);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(184, 102, 100, 30);
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeProduto = textProduto.getText();
                double preco = Double.parseDouble(textPreco.getText());

                
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = null;
                String sql = "INSERT INTO produtos (preco, descricao) VALUES (?, ?)";

                try {
                    stmt = conn.prepareStatement(sql);
                    stmt.setDouble(1, preco);
                    stmt.setString(2, nomeProduto);

                    stmt.executeUpdate();
                    System.out.println("Produto adicionado com sucesso!");
                } catch (SQLException ex) {
                    System.out.println("Erro ao adicionar produto: " + ex.getMessage());
                } finally {
                    try {
                        if (stmt != null)
                            stmt.close();
                        if (conn != null)
                            conn.close();
                    } catch (SQLException ex) {
                        System.out.println("Erro ao fechar conexão: " + ex.getMessage());
                    }
                }

                dispose();
            }
        });
        contentPane.add(btnAdicionar);

        textPreco = new JTextField();
        textPreco.setColumns(10);
        textPreco.setBounds(10, 49, 181, 20);
        contentPane.add(textPreco);

        textProduto = new JTextField();
        textProduto.setColumns(10);
        textProduto.setBounds(271, 49, 181, 20);
        contentPane.add(textProduto);

        lblId = new JLabel("Preço");
        lblId.setForeground(SystemColor.text);
        lblId.setBounds(10, 24, 142, 14);
        contentPane.add(lblId);

        lblNomeProduto = new JLabel("Nome Produto");
        lblNomeProduto.setForeground(SystemColor.text);
        lblNomeProduto.setBounds(271, 24, 123, 14);
        contentPane.add(lblNomeProduto);
    }
}
