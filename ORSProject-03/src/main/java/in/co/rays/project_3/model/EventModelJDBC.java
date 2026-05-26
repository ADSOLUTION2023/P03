package in.co.rays.project_3.model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class EventModelJDBC implements EventModelInt {

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_EVENT");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
				System.out.println(pk);
			}
		} catch (Exception e) {
			throw new DatabaseException("Database Exception" + e);

		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;

	}

	@Override
	public long add(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;
		EventDTO existDto = null;
		existDto = findByEvent(dto.getEventName());
		EventDTO existDto1 = findByParticipent(dto.getParticipantName());
		if (existDto != null && existDto1 != null) {
			throw new DuplicateRecordException("Participant already Registered for the event");
		}
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			pk = nextPK();
			PreparedStatement ps = con.prepareStatement("insert into ST_EVENT values(?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getParticipantName());
			ps.setString(3, dto.getEventName());
			ps.setString(4, dto.getEmail());
			ps.setDate(5, (Date) dto.getRegistrationDate());
			/*
			 * ps.setString(15, dto.getCreatedBy()); ps.setString(16, dto.getModifiedBy());
			 * ps.setTimestamp(17, dto.getCreatedDatetime()); ps.setTimestamp(18,
			 * dto.getModifiedDatetime());
			 */
			ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
				throw new ApplicationException("exception: add rollback exception:" + e2.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add Event");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk;
	}

	@Override
	public void update(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;
		EventDTO existDto = null;
		existDto = findByEvent(dto.getEventName());
		EventDTO existDto1 = findByParticipent(dto.getParticipantName());
		if (existDto != null && existDto1 != null) {
			throw new DuplicateRecordException("Participant already Registered for the event");
		}
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			pk = nextPK();
			PreparedStatement ps = con.prepareStatement(
					"update ST_EVENT set PARTICIPANT_NAME=?,EVENT_NAME=?,EMAIL=?,REGISTRATION_DATE=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(2, dto.getParticipantName());
			ps.setString(3, dto.getEventName());
			ps.setString(4, dto.getEmail());
			ps.setDate(5, (Date) dto.getRegistrationDate());
			ps.setString(15, dto.getCreatedBy());
			ps.setString(16, dto.getModifiedBy());
			ps.setTimestamp(17, dto.getCreatedDatetime());
			ps.setTimestamp(18, dto.getModifiedDatetime());

			ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
				throw new ApplicationException("exception: add rollback exception:" + e2.getMessage());

			}
			throw new ApplicationException("Exception : Exception in updating Event");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

	}

	@Override
	public void delete(EventDTO dto) throws ApplicationException {
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from ST_EVENT where id=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Event");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList array = null;
		EventDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from ST_EVENT where 1=1");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new EventDTO();
				dto.setId(rs.getLong(1));
				dto.setParticipantName(rs.getString(2));
				dto.setEventName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setRegistrationDate(rs.getDate(5));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				array.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Events");
		} finally {
			JDBCDataSource.closeConnection(con);
			return array;
		}
	}

	@Override
	public List search(EventDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		ArrayList list = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM EVENT WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}

			if (dto.getParticipantName() != null && dto.getParticipantName().length() > 0) {

				sql.append(" AND PARTICIPANT_NAME like '" + dto.getParticipantName() + "%'");
			}

			if (dto.getEventName() != null && dto.getEventName().length() > 0) {

				sql.append(" AND EVENT_NAME like '" + dto.getEventName() + "%'");
			}

			if (dto.getEmail() != null && dto.getEmail().length() > 0) {

				sql.append(" AND EMAIL like '" + dto.getEmail() + "%'");
			}

			if (dto.getRegistrationDate() != null) {

				sql.append(" AND REGISTRATION_DATE = '" + new java.sql.Date(dto.getRegistrationDate().getTime()) + "'");
			}
		}

		// Pagination
		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		list = new ArrayList();

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new EventDTO();

				dto.setId(rs.getLong("ID"));
				dto.setParticipantName(rs.getString("PARTICIPANT_NAME"));
				dto.setEventName(rs.getString("EVENT_NAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setRegistrationDate(rs.getDate("REGISTRATION_DATE"));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Event Search");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public List search(EventDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public EventDTO findByPk(long pk) throws ApplicationException {
		Connection con = null;
		PreparedStatement ps = null;
		EventDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_EVENT where id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new EventDTO();
				dto.setId(rs.getLong(1));
				dto.setParticipantName(rs.getString(2));
				dto.setEventName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setRegistrationDate(rs.getDate(5));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Event by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return dto;
	}


	@Override
	public EventDTO findByParticipent(String participentName) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		EventDTO dto = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM EVENT WHERE PARTICIPANT_NAME = ?");

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ps.setString(1, participentName);

			rs = ps.executeQuery();

			while (rs.next()) {

				dto = new EventDTO();

				dto.setId(rs.getLong("ID"));
				dto.setParticipantName(rs.getString("PARTICIPANT_NAME"));
				dto.setEventName(rs.getString("EVENT_NAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setRegistrationDate(rs.getDate("REGISTRATION_DATE"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in finding Event by Participant Name");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public EventDTO findByEvent(String eventName) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		EventDTO dto = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM EVENT WHERE EVENT_NAME = ?");

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ps.setString(1, eventName);

			rs = ps.executeQuery();

			while (rs.next()) {

				dto = new EventDTO();

				dto.setId(rs.getLong("ID"));
				dto.setParticipantName(rs.getString("PARTICIPANT_NAME"));
				dto.setEventName(rs.getString("EVENT_NAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setRegistrationDate(rs.getDate("REGISTRATION_DATE"));
			}

		} catch (Exception e) {

		

			throw new ApplicationException("Exception in finding Event by Event Name");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}
}

