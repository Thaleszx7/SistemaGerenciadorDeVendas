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

public class NovoCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textProduto;
    private JLabel lblNomeProduto;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NovoCliente frame = new NovoCliente();
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
    public NovoCliente() {
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

                Connection conn = Conexao.conectar();
                PreparedStatement stmt = null;
                String sql = "INSERT INTO clientes (nome) VALUES (?)";

                try {
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nomeProduto);

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

        textProduto = new JTextField();
        textProduto.setColumns(10);
        textProduto.setBounds(10, 49, 442, 20);
        contentPane.add(textProduto);

        lblNomeProduto = new JLabel("Nome Cliente");
        lblNomeProduto.setForeground(SystemColor.text);
        lblNomeProduto.setBounds(10, 24, 123, 14);
        contentPane.add(lblNomeProduto);
    }
}
