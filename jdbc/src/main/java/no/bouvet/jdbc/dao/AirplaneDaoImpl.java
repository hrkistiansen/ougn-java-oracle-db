package no.bouvet.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.domain.Airplane;

public class AirplaneDaoImpl implements AirplaneDao {
	private DataSource dataSource;

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public void create(Airplane airplane) {
		String insertStatement = "insert into airplane (id, name, created, created_by) "
				+ "values (?,?,?,?)";
		Long airplaneId = getNextId();
		
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertStatement);
			statement.setLong(1, airplaneId);
			statement.setString(2, airplane.getName());
			statement.setDate(3, new java.sql.Date(new Date().getTime()));
			statement.setString(4, System.getProperty("user.name"));
			int state = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Airplane airplane) {
		String updateStatement = "update airplane set name = ?, last_updated = ?, last_updated_by = ? where id = ?";
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);
			statement.setString(1, airplane.getName());
			statement.setDate(2, new java.sql.Date(new Date().getTime()));
			statement.setString(3, System.getProperty("user.name"));
			statement.setLong(4, airplane.getId());
			int state = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Airplane airplane) {
		String deleteStatement = "delete airplane where id = ?";
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setLong(1, airplane.getId());
			int state = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Airplane getById(Long airplaneId) {
		String query = "select * from airplane where id = ?";
		Airplane airplane = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setLong(1, airplaneId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				airplane = resultSetToAirplaneMapper(resultSet);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return airplane;
	}

	@Override
	public List<Airplane> getAll() {
		String query = "select * from airplane";
		List<Airplane> airplanes = new ArrayList<Airplane>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery("select * from airplane");
			while (resultSet.next()) {
				Airplane airplane = resultSetToAirplaneMapper(resultSet);
				airplanes.add(airplane);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return airplanes;
	}

	private Airplane resultSetToAirplaneMapper(ResultSet rs) throws SQLException {
		Airplane airplane = new Airplane(rs.getString("name"));
		airplane.setId(rs.getLong("id"));
		airplane.setCreated(rs.getDate("created"));
		airplane.setCreatedBy(rs.getString("created_by"));
		airplane.setLastUpdated(rs.getDate("last_updated"));
		airplane.setLastUpdatedBy(rs.getString("last_updated_by"));
		return airplane;
	}

	private void closeAll() {
		try {
			connection.close();
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Long getNextId() {
		String query = "select hibernate_sequence.nextval next_id from dual";
		Long nextId = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nextId = resultSet.getLong("next_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nextId;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
