package SistemaDeVendas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import View.NovoProduto;
import View.PesquisarProduto;

import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import SistemaDeVendas.Conexao;

public class ProdutoJ extends JFrame {

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
                    ProdutoJ frame = new ProdutoJ();
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
    public ProdutoJ() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NovoProduto novoProdutoFrame = new NovoProduto();
                novoProdutoFrame.setVisible(true);
            }
        });
        btnNovo.setForeground(SystemColor.textHighlight);
        btnNovo.setBounds(10, 33, 89, 33);
        contentPane.add(btnNovo);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PesquisarProduto pesquisarProdutoFrame = new PesquisarProduto();
                pesquisarProdutoFrame.setVisible(true);
            }
        });
        btnPesquisar.setForeground(SystemColor.textHighlight);
        btnPesquisar.setBounds(108, 33, 89, 33);
        contentPane.add(btnPesquisar);

        JButton btnExcluir_1 = new JButton("Excluir");
        btnExcluir_1.setForeground(SystemColor.textHighlight);
        btnExcluir_1.setBounds(335, 33, 89, 33);
        btnExcluir_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    excluirProduto(id);
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                }
            }
        });
        
        
        
        
        contentPane.add(btnExcluir_1);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });
        btnAtualizar.setForeground(SystemColor.textHighlight);
        btnAtualizar.setBounds(236, 33, 89, 33);
        contentPane.add(btnAtualizar);

        JLabel lblNewLabel_1 = new JLabel("Produtos");
        lblNewLabel_1.setForeground(SystemColor.text);
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblNewLabel_1.setBounds(181, 8, 71, 14);
        contentPane.add(lblNewLabel_1);

        table = new JTable();
        table.setBounds(10, 77, 414, 153);
        contentPane.add(table);
    }

    private void atualizarTabela() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Descrição");
        model.addColumn("Valor");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT * FROM produtos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("preco");
                model.addRow(new Object[]{id, descricao, valor});
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
    private void excluirProduto(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "DELETE FROM produtos WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
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
