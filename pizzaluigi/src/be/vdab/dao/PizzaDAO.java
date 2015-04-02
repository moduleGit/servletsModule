package be.vdab.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.Pizza;

/*import java.math.BigDecimal;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import java.util.Map;
 import java.util.concurrent.ConcurrentHashMap;
 import be.vdab.entities.Pizza;

 public class PizzaDAO {
 private static final Map<Long, Pizza> PIZZAS = new ConcurrentHashMap<>();
 static {
 PIZZAS.put(12L,
 new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true));
 PIZZAS.put(14L, new Pizza(14, "Margehrita", BigDecimal.valueOf(5),
 false));
 PIZZAS.put(17L, new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
 PIZZAS.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5),
 false));
 }

 public List<Pizza> findAll() {
 return new ArrayList<>(PIZZAS.values());
 }

 public Pizza read(long id) {
 return PIZZAS.get(id);
 }

 public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
 List<Pizza> pizzas = new ArrayList<>();
 for (Pizza pizza : PIZZAS.values()) {
 if (pizza.getPrijs().compareTo(van) >= 0
 && pizza.getPrijs().compareTo(tot) <= 0) {
 pizzas.add(pizza);
 }
 }
 return pizzas;
 }

 public void create(Pizza pizza) { // pizza toevoegen
 pizza.setId(Collections.max(PIZZAS.keySet()) + 1);
 PIZZAS.put(pizza.getId(), pizza);
 }

 }*/

public class PizzaDAO extends AbstractDAO {
	private static final String BEGIN_SELECT = "select id, naam, prijs, pikant from pizzas ";
	private static final String FIND_ALL_SQL = BEGIN_SELECT + "order by naam";
	private static final String READ_SQL = BEGIN_SELECT + "where id=?";
	private static final String FIND_BY_PRIJS_BETWEEN_SQL = BEGIN_SELECT
			+ "where prijs between ? and ? order by prijs";
	private static final String CREATE_SQL = "insert into pizzas(naam, prijs, pikant) values (?, ?, ?)";

	public List<Pizza> findAll() {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			List<Pizza> pizzas = new ArrayList<>();
			while (resultSet.next()) {
				pizzas.add(resultSetRijNaarPizza(resultSet));
			}
			return pizzas;
		} catch (SQLException ex) { 							// (1)
			throw new DAOException(ex); 						// (2)
		}
	}

	private Pizza resultSetRijNaarPizza(ResultSet resultSet) 	// (3)
			throws SQLException { 								// (4)
		return new Pizza(resultSet.getLong("id"), resultSet.getString("naam"),
				resultSet.getBigDecimal("prijs"),
				resultSet.getBoolean("pikant"));
	}

	public Pizza read(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(READ_SQL)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetRijNaarPizza(resultSet);
				}
				return null;
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
	}

	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(FIND_BY_PRIJS_BETWEEN_SQL)) {
			List<Pizza> pizzas = new ArrayList<>();
			statement.setBigDecimal(1, van);
			statement.setBigDecimal(2, tot);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					pizzas.add(resultSetRijNaarPizza(resultSet));
				}
				return pizzas;
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
	}

	public void create(Pizza pizza) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, pizza.getNaam());
			statement.setBigDecimal(2, pizza.getPrijs());
			statement.setBoolean(3, pizza.isPikant());
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				pizza.setId(resultSet.getLong(1));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
	}
}