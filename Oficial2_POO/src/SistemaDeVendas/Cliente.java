package SistemaDeVendas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import View.NovoCliente;
import View.PesquisarCliente;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cliente frame = new Cliente();
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
    public Cliente() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNovo = new JButton("Novo");
        btnNovo.setForeground(SystemColor.textHighlight);
        btnNovo.setBounds(10, 28, 89, 33);
        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NovoCliente novoCliente = new NovoCliente();
                novoCliente.setVisible(true);
            }
        });

        contentPane.add(btnNovo);

        JButton btnNovo_1 = new JButton("Pesquisar");
        btnNovo_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PesquisarCliente pesquisarCliente = new PesquisarCliente();
                pesquisarCliente.setVisible(true);
            }
        });
        btnNovo_1.setForeground(SystemColor.textHighlight);
        btnNovo_1.setBounds(109, 28, 89, 33);
        contentPane.add(btnNovo_1);

        JButton btnNovo_2 = new JButton("Atualizar");
        btnNovo_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });
        btnNovo_2.setForeground(SystemColor.textHighlight);
        btnNovo_2.setBounds(236, 28, 89, 33);
        contentPane.add(btnNovo_2);

        JButton btnNovo_3 = new JButton("Excluir");
        btnNovo_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String nome = (String) table.getValueAt(selectedRow, 0);
                    excluirCliente(nome);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente para excluir.");
                }
            }
        });
        btnNovo_3.setForeground(SystemColor.textHighlight);
        btnNovo_3.setBounds(335, 28, 89, 33);
        contentPane.add(btnNovo_3);

        JPanel panel = new JPanel();
        panel.setBounds(10, 72, 414, 178);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Clientes");
        lblNewLabel_1.setBounds(180, 11, 62, 14);
        panel.add(lblNewLabel_1);
        lblNewLabel_1.setForeground(new Color(0, 0, 0));
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));

        // Adiciona a tabela ao painel
        table = new JTable();
        table.setBounds(10, 30, 394, 138);
        panel.add(table);
    }

    // metodo que vai atualizar a tabela apenas com o nome dosc clientes
    private void atualizarTabela() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nome");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                model.addRow(new Object[]{nome});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        table.setModel(model);
    }
    private void excluirCliente(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "DELETE FROM clientes WHERE nome = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
                atualizarTabela(); // atualizar a tabela depois de excluir o item desejado
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
    

