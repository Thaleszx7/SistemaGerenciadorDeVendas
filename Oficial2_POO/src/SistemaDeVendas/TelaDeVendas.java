package SistemaDeVendas;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TelaDeVendas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idProd;
    private JTextField campQuantidade;
    private JComboBox<String> clienteComboBox;
    private JComboBox<String> produtoComboBox;
    private JTable tabelaPedidos;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaDeVendas frame = new TelaDeVendas();
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
    public TelaDeVendas() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        idProd = new JTextField();
        idProd.setBounds(10, 36, 128, 20);
        idProd.setEditable(false); // o campo vai ser preenchido automaticamente após a execução da função, mas puxara o ultimo id feito.
        contentPane.add(idProd);
        idProd.setColumns(10);

        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setBounds(10, 11, 66, 14);
        contentPane.add(lblNewLabel);

        clienteComboBox = new JComboBox<>();
        clienteComboBox.setBounds(175, 35, 249, 22);
        contentPane.add(clienteComboBox);

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setBounds(175, 11, 66, 14);
        contentPane.add(lblCliente);

        produtoComboBox = new JComboBox<>();
        produtoComboBox.setBounds(10, 92, 166, 22);
        contentPane.add(produtoComboBox);

        JLabel lblProduto = new JLabel("Produto");
        lblProduto.setBounds(10, 67, 66, 14);
        contentPane.add(lblProduto);

        campQuantidade = new JTextField();
        campQuantidade.setBounds(196, 92, 86, 22);
        contentPane.add(campQuantidade);
        campQuantidade.setColumns(10);

        JLabel lblQuantidade = new JLabel("Quantd.");
        lblQuantidade.setBounds(196, 67, 66, 14);
        contentPane.add(lblQuantidade);

        JButton btnAddItem = new JButton("Adicionar");
        btnAddItem.setBounds(319, 92, 105, 22);
        contentPane.add(btnAddItem);

        JPanel painelPedidos = new JPanel();
        painelPedidos.setBounds(10, 137, 564, 175);
        contentPane.add(painelPedidos);
        painelPedidos.setLayout(null);

        tabelaPedidos = new JTable();
        tabelaPedidos.setBounds(10, 30, 544, 134);
        painelPedidos.add(tabelaPedidos);

        JButton btnListarPedidos = new JButton("Listar Pedidos");
        btnListarPedidos.setBounds(162, 323, 120, 22);
        contentPane.add(btnListarPedidos);

        
        carregarClientes();
        carregarProdutos();
        atualizarIdProd(); // 

        // Action Listener para adicionar itens
        btnAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarItem();
                atualizarIdProd(); //vai atualizar o idProd com a quantidade de pedidos atual
            }
        });

      
        btnListarPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarPedidos();
            }
        });
    }

    // Método para carregar os clientes no combobox
    private void carregarClientes() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT id, nome FROM clientes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                clienteComboBox.addItem(nome);
                clienteComboBox.putClientProperty(nome, id);  // Guarda o ID do cliente
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
    }

    // metodo pra carregar os produtos no combobox (isso aqui é tortura eliel)
    private void carregarProdutos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT id, descricao, preco FROM produtos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                produtoComboBox.addItem(descricao);
                produtoComboBox.putClientProperty(descricao, new Produto(id, preco));  
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
    }

    // metodo que vai atualizar o idProd com a quantidade de pedidos atual
    private void atualizarIdProd() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT MAX(id) FROM pedidos");
            rs = stmt.executeQuery();

            if (rs.next()) {
                int maxId = rs.getInt(1);
                idProd.setText(String.valueOf(maxId + 1));
            } else {
                idProd.setText("1"); // Se não houver pedidos começar do ID 1
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
    }

    //metodo que vai adicionar um item e vincular ele ao pedido
    private void adicionarItem() {
        Connection conn = null;
        PreparedStatement pedidoStmt = null;
        PreparedStatement itemStmt = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            // adicionar um pedido 
            String clienteNome = (String) clienteComboBox.getSelectedItem();
            int clienteId = (int) clienteComboBox.getClientProperty(clienteNome);
            LocalDate dataAtual = LocalDate.now();

            String pedidoSql = "INSERT INTO pedidos (dtCadastro, ClienteId) VALUES (?, ?)";
            pedidoStmt = conn.prepareStatement(pedidoSql, PreparedStatement.RETURN_GENERATED_KEYS);
            pedidoStmt.setDate(1, java.sql.Date.valueOf(dataAtual));
            pedidoStmt.setInt(2, clienteId);
            pedidoStmt.executeUpdate();

            ResultSet pedidoRs = pedidoStmt.getGeneratedKeys();
            int pedidoId = 0;
            if (pedidoRs.next()) {
                pedidoId = pedidoRs.getInt(1);
            }

            // inserir o item do pedido
            String produtoNome = (String) produtoComboBox.getSelectedItem();
            Produto produto = (Produto) produtoComboBox.getClientProperty(produtoNome);
            int produtoId = produto.getId();
            double preco = produto.getPreco();
            int quantidade = Integer.parseInt(campQuantidade.getText());

            String itemSql = "INSERT INTO itens (PedidoId, ProdutoId, Preco, Quantidade) VALUES (?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(itemSql);
            itemStmt.setInt(1, pedidoId);
            itemStmt.setInt(2, produtoId);
            itemStmt.setDouble(3, preco);
            itemStmt.setInt(4, quantidade);
            itemStmt.executeUpdate();

            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (pedidoStmt != null) pedidoStmt.close();
                if (itemStmt != null) itemStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // metodo pra listar os produtos no jtable
    private void listarPedidos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Data");
        model.addColumn("Produto");
        model.addColumn("Cliente");
        model.addColumn("Preço");
        model.addColumn("Quantidade");
        model.addColumn("Total");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT p.dtCadastro, pr.descricao, c.nome, i.Preco, i.Quantidade, " +
                         "(i.Preco * i.Quantidade) AS Total " +
                         "FROM pedidos p " +
                         "JOIN itens i ON p.id = i.PedidoId " +
                         "JOIN produtos pr ON i.ProdutoId = pr.id " +
                         "JOIN clientes c ON p.ClienteId = c.id " +
                         "WHERE p.dtCadastro = CURRENT_DATE " +
                         "ORDER BY p.dtCadastro, pr.descricao, c.nome, i.Preco, i.Quantidade";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate data = rs.getDate("dtCadastro").toLocalDate();
                String produto = rs.getString("descricao");
                String cliente = rs.getString("nome");
                double preco = rs.getDouble("Preco");
                int quantidade = rs.getInt("Quantidade");
                double total = rs.getDouble("Total");
                model.addRow(new Object[]{data, produto, cliente, preco, quantidade, total});
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

        tabelaPedidos.setModel(model);
    }

    // Classe que vai armazenar os pedidos internamento no codigo
    private class Produto {
        private int id;
        private double preco;

        public Produto(int id, double preco) {
            this.id = id;
            this.preco = preco;
        }

        public int getId() {
            return id;
        }

        public double getPreco() {
            return preco;
        }
    }
}
