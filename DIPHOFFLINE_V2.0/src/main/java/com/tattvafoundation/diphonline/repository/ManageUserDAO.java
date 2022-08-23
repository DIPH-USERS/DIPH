package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.AllUsersBean;
import com.tattvafoundation.diphonline.model.DeleteUserBean;
import com.tattvafoundation.diphonline.model.ManageCreateUserReturnStatusBean;
import com.tattvafoundation.diphonline.model.User_Info;
import com.tattvafoundation.diphonline.model.ViewExistingUsers;

@Repository
public class ManageUserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ManageCreateUserReturnStatusBean deleteuser(DeleteUserBean model) {
		ManageCreateUserReturnStatusBean result = new ManageCreateUserReturnStatusBean();

		try {
			String sql_check_permit = "SELECT `user_id`, `user_nm`, `user_pass`,`district_id`, `can_create`, `can_view`, `can_edit`, `can_delete` FROM `user_info` where `user_nm`=?";
			Object[] params_check_permit = new Object[] { model.getLoggedUser() };

			String permission = jdbcTemplate.query(sql_check_permit, params_check_permit, rs -> {

				String permit = "";
				while (rs.next()) {

					String can_delete = rs.getString("can_delete");
					permit = can_delete;

				}
				/* We can also return any variable-data from here but not used currently */
				return permit;
			});

			if ("1".equals(permission.trim())) {
				
				String user_existing_p_key = jdbcTemplate.query("SELECT * from  user_info  WHERE `user_nm` = ?",
						new Object[] { model.getUsername() }, rs2 -> {

							String pkey = "";

							while (rs2.next()) {
								pkey = rs2.getString("user_id");
							}
							/* We can also return any variable-data from here but not used currently */
							return pkey;
						});
				
				
				String sql1 = " DELETE FROM `user_info` WHERE (`user_nm` = ?);";

				int row = 0;
				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, model.getUsername());
					return ps;
				});
				
				String delete_sql = " DELETE FROM `geographical_combination` WHERE (`user_info_id` = ?)";

				jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(delete_sql);
					ps.setString(1, user_existing_p_key);
					return ps;
				});
				

				if (row > 0) {
					result.setStatus("deleted");
					result.setMessage("User deleted successfully!!");
				} else {
					result.setStatus("not_allowed");
					result.setMessage("Server Error");
				}
			} else {
				result.setStatus("not_allowed");
				result.setMessage("User does not have sufficient priviledges.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("not_allowed");
			result.setMessage("Server Error");
			// TODO: handle exception
		}

		return result;
	}

	public ViewExistingUsers viewusers(String loggedUser, String loggedUserDistrict) {
		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		ViewExistingUsers response = new ViewExistingUsers();

		try {

			String sql_check_permit = "SELECT `user_id`, `user_nm`, `user_pass`,`district_id`, `can_create`, `can_view`, `can_edit`, `can_delete` FROM `user_info` where `user_nm`=?";
			Object[] params_check_permit = new Object[] { loggedUser };

			String permission = jdbcTemplate.query(sql_check_permit, params_check_permit, rs -> {

				String permit = "";
				while (rs.next()) {

					String can_view = rs.getString("can_view");
					permit = can_view;

				}
				/* We can also return any variable-data from here but not used currently */
				return permit;
			});

			if ("1".equals(permission.trim())) {

				List<AllUsersBean> alluserslist = jdbcTemplate.query(
						"SELECT `user_id`, `user_nm`, `user_pass`,`user_status`,`emailId`, `can_create`, `can_view`, `can_edit`, `can_delete` FROM `user_info` ",
						new Object[] {}, rs -> {

							List<AllUsersBean> list = new ArrayList<>();

							while (rs.next()) {
								AllUsersBean obj = new AllUsersBean();
								obj.setUser_nm(rs.getString("user_nm"));
								obj.setUser_pass("");
								obj.setEmailId(rs.getString("emailId"));
								obj.setUser_status(rs.getString("user_status"));
								obj.setCan_create(rs.getString("can_create"));
								obj.setCan_view(rs.getString("can_view"));
								obj.setCan_edit(rs.getString("can_edit"));
								obj.setCan_delete(rs.getString("can_delete"));

								List<String> list1 = new ArrayList<>();
								List<String> list2 = new ArrayList<>();
								List<String> list3 = new ArrayList<>();

								/*------------------------------------------*/

								jdbcTemplate.query(
										"SELECT `id`,    `user_info_id`,    `district_id`,    `cycle_id`,    `year`  FROM  `geographical_combination` where `user_info_id`=? ",
										new Object[] { rs.getString("user_id") }, rs2 -> {

											while (rs2.next()) {
												list1.add(rs2.getString("district_id"));
												list2.add(rs2.getString("cycle_id"));
												list3.add(rs2.getString("year"));
											}
											/* We can also return any variable-data from here but not used currently */
											return "";
										});
								/*-----------------------------------------*/

								obj.setDistrict(list1);
								obj.setCycle(list2);
								obj.setYear(list3);

								list.add(obj);

							}
							/* We can also return any variable-data from here but not used currently */
							return list;
						});

				response.setStatus("true");
				response.setMessage("All users username retrieved.");

				response.setUserList(alluserslist);
			} else {
				response.setStatus("false");
				response.setMessage("User doesnt have priviledges to view other users");
				response.setUserList(new ArrayList<>());

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("false");
			response.setMessage("Server Error occured");
			// TODO: handle exception
		}

		return response;
	}

	public ManageCreateUserReturnStatusBean updateuser(User_Info model) {

	
		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		ManageCreateUserReturnStatusBean response = new ManageCreateUserReturnStatusBean();

	
		String user_existing_p_key = jdbcTemplate.query("SELECT * from  user_info  WHERE `user_nm` = ?",
				new Object[] { model.getUsername() }, rs2 -> {

					String pkey = "";

					while (rs2.next()) {
						pkey = rs2.getString("user_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return pkey;
				});

		
		int row;
		
		
		if(model.getUser_password().trim().length() >= 4) {
		
		String encodedPassword = passwordEncoder.encode(model.getUser_password());
		model.setUser_password(encodedPassword);
			
		String sql1 = " UPDATE    `user_info` SET `user_pass` = ?," + " `can_create` = ?,`can_view` = ?,`can_edit` = ?,"
				+ " `can_delete` = ?,`emailId` = ? WHERE `user_nm` = ?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getUser_password());
			ps.setString(2, model.getCreateUser());
			ps.setString(3, model.getViewUser());
			ps.setString(4, model.getEditUser());
			ps.setString(5, model.getDeleteUser());
			ps.setString(6, model.getEmail());
			ps.setString(7, model.getUsername());
			return ps;
		});
		
		}else {
			
			String sql2 = " UPDATE    `user_info` SET `can_create` = ?,`can_view` = ?,`can_edit` = ?,"
					+ " `can_delete` = ?,`emailId` = ? WHERE `user_nm` = ?";

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql2);
				ps.setString(2, model.getCreateUser());
				ps.setString(3, model.getViewUser());
				ps.setString(4, model.getEditUser());
				ps.setString(5, model.getDeleteUser());
				ps.setString(6, model.getEmail());
				ps.setString(7, model.getUsername());
				return ps;
			});
		}
		
		
		
		
		
		
		
		
		
		
		

		if (row > 0) {

			// SELECT `id`, `user_info_id`, `district_id`, `cycle_id`, `year` FROM
			// `geographical_combination` where `user_info_id`=8;

			/*************/

			for (int x = 0; x < model.getDistrict().size(); x++) {

				for (int y = 0; y < model.getCycle().size(); y++) {

					for (int z = 0; z < model.getYear().size(); z++) {

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						String checkpkey = jdbcTemplate.query(
								"SELECT `id`,    `user_info_id`,    `district_id`,    `cycle_id`,    `year`  FROM  `geographical_combination` where `user_info_id`=? and `district_id`=?  and  `cycle_id`=?  and   `year`=?",
								new Object[] { user_existing_p_key, model.getDistrict().get(x), model.getCycle().get(y),
										model.getYear().get(z) },
								rs2 -> {

									String pkey = "";

									while (rs2.next()) {
										pkey = rs2.getString("id");
									}
									/* We can also return any variable-data from here but not used currently */
									return pkey;
								});

						if ("".equals(checkpkey)) {
							String sql2 = "INSERT INTO `geographical_combination`(`user_info_id`,`district_id`,`cycle_id`,`year`)  "
									+ " VALUES (?,?,?,?)";
							final int index1 = x;
							final int index2 = y;
							final int index3 = z;

							jdbcTemplate.update(connection -> {

								PreparedStatement ps = connection.prepareStatement(sql2,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + user_existing_p_key);
								ps.setString(2, "" + model.getDistrict().get(index1));
								ps.setString(3, "" + model.getCycle().get(index2));
								ps.setString(4, "" + model.getYear().get(index3));

								return ps;
							}, keyHolder2);
						}

					}

				}

			}
			/*************/
			
			
			List<String> dst_arr = model.getDistrict();
			
			List<String> cycle_arr = model.getCycle();
			
			List<String> year_arr = model.getYear();
			
			 jdbcTemplate.query(
					"SELECT     `user_info_id`,    `district_id`,    `cycle_id`,    `year`  FROM  `geographical_combination` where `user_info_id`=?",
					new Object[] { user_existing_p_key },
					rs2 -> {

						String pkey = "";
						
					
						while (rs2.next()) {
							
						
							
							for (int x = 0; x < dst_arr.size(); x++)  
							{

								for (int y = 0; y < cycle_arr.size(); y++) 
								{

									for (int z = 0; z < year_arr.size(); z++) 
									{
									String s1 =	model.getDistrict().get(x);
									String s2 =	model.getCycle().get(y);
									String s3 =	model.getYear().get(z);
									
									if(s1.equals(""+rs2.getString("district_id") ) && s2.equals(""+rs2.getString("cycle_id")) && s3.equals(""+rs2.getString("year"))) {
									
									 
									}
									else 
									{
																			
										
										String delete_sql = " DELETE FROM `geographical_combination` WHERE (`user_info_id` = ? and `district_id`=?  and `cycle_id`=?  and `year`=? )";

										jdbcTemplate.update(connection -> {
											PreparedStatement ps = connection.prepareStatement(delete_sql);
											ps.setString(1, user_existing_p_key);
											ps.setString(2, rs2.getString("district_id") );
											ps.setString(3, rs2.getString("cycle_id") );
											ps.setString(4, rs2.getString("year") );
											return ps;
										});
									}
									
									}//Last Loop
								}//Middle Loop
							}//First Loop
							
						}
						/* We can also return any variable-data from here but not used currently */
						return pkey;
					});
			

//			String sql2 = " UPDATE    `user_info` SET `user_pass` = ?,"
//					+ " `can_create` = ?,`can_view` = ?,`can_edit` = ?,"
//					+ " `can_delete` = ?,`emailId` = ? WHERE `user_nm` = ?";
//
//			int row2 = jdbcTemplate.update(connection -> {
//				PreparedStatement ps = connection.prepareStatement(sql2);
////				ps.setString(1, model.getUser_password());
//				return ps;
//			});

			
		}

		return response;
	}

	public ManageCreateUserReturnStatusBean createuser(User_Info model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		ManageCreateUserReturnStatusBean response = new ManageCreateUserReturnStatusBean();

	
		
		String permission = "1";

//		String sql_check_permit = "SELECT `user_id`, `user_nm`, `user_pass`,`district_id`, `can_create`, `can_view`, `can_edit`, `can_delete` FROM `user_info` where `user_nm`=?";
//		Object[] params_check_permit = new Object[] { model.getLoggedUser() };
//
//		String permission = jdbcTemplate.query(sql_check_permit, params_check_permit, rs -> {
//
//			String permit = "";
//			while (rs.next()) {
//
//				String can_create = rs.getString("can_create");
//				permit = can_create;
//
//			}
//			
//			return permit;
//		});

		if ("1".equals(permission.trim())) {
			// can create new Users

			// Checking if new user already exists in Db or not
			String sql1 = "SELECT `user_id`, `user_nm`, `user_pass`,`district_id`, `can_create`, `can_view`, `can_edit`, `can_delete` FROM `user_info` where `user_nm`=?";
			Object[] params1 = new Object[] { model.getUsername() };

			int flag = jdbcTemplate.query(sql1, params1, rs -> {

				int count = 0;
				while (rs.next()) {

					String user_id = rs.getString("user_id");
					
					count++;
				}
				/* We can also return any variable-data from here but not used currently */
				return count;
			});

			if (flag != 0) {
				// user already exists
				response.setStatus("exists");
				response.setMessage("User Already Created.");
			} else {
				// new user allowed to be created

				String sql = "INSERT INTO `user_info`(`user_nm`, `user_pass`, `district_id`, `can_create`,"
						+ " `can_view`, `can_edit`, `can_delete`,`emailId`,`user_status`) VALUES (?,?,?,?,"
						+ "?,?,?,?,?)";
				KeyHolder keyHolder = new GeneratedKeyHolder();

				jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model.getUsername());
					ps.setString(2, model.getUser_password());
					ps.setString(3, "0");
					ps.setString(4, "1");
					ps.setString(5, "1");
					ps.setString(6, "1");
					ps.setString(7, "0");
					ps.setString(8, model.getEmail());
					ps.setString(9, model.getUserStatus());

					return ps;
				}, keyHolder);

				long p_key_new = keyHolder.getKey().longValue();

				List<String> districts = model.getDistrict();

				List<String> cycles = model.getCycle();

				List<String> years = model.getYear();

				String sql2 = "INSERT INTO `geographical_combination`(`user_info_id`,`district_id`,`cycle_id`,`year`)  "
						+ " VALUES (?,?,?,?)";

				for (int x = 0; x < districts.size(); x++) {

					for (int y = 0; y < cycles.size(); y++) {

						for (int z = 0; z < years.size(); z++) {

							KeyHolder keyHolder2 = new GeneratedKeyHolder();

							final int index1 = x;
							final int index2 = y;
							final int index3 = z;

							jdbcTemplate.update(connection -> {

								PreparedStatement ps = connection.prepareStatement(sql2,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key_new);
								ps.setString(2, "" + districts.get(index1));
								ps.setString(3, "" + cycles.get(index2));
								ps.setString(4, "" + years.get(index3));

								return ps;
							}, keyHolder2);

						}

					}

				}

				// new user successfully created
				response.setStatus("created");
				response.setMessage("New User Created.");
			}
		} else {
			// cannot create
			response.setStatus("not_allowed");
			response.setMessage("User can not be created");
		}

		// ResultSet rs = ps.getGeneratedKeys();

		// long p_key = keyHolder.getKey().longValue();

		return response;
	}
}
