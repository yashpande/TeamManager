package csIA_TeamManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import ca.odell.glazedlists.*;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.*;

/**
 * @author 000220-0086
 * @date 19 January, 2014
 * @purpose The TeamManager allows a coach to manage his team by storing their game results, scoring their player on criterion, and
 * using that information to develop a schedule to improve their team.
 * @IDE Eclipse Luna
 * @Platform Mac OSX Yosemite Version 10.10
 */

public class TeamManager extends JFrame
{
	private static final long serialVersionUID = 232342343241L;
	private static ArrayList <Team> teamlist;
	static JPanel biggestPanel,mainview,removePanel,titlePanel,playereditpanel,teamCreator,teamview,rosterpanel,gamepanel,playeradd,addcriterion,criterionview,playerview;
	static boolean showwelcomemessage = true;
	int screenWidth,screenHeight;
	@SuppressWarnings("unchecked")
	/**
	 * @purpose Attempts to read welcomemessage and teamlist from files, if these files do not exist 
	 * it creates the files and populates them with default variables.
	 */
	public static void main(String[] args) 
	{
		try
		{
			File welcomemessagepath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","welcomemessage.ser");
			if(!welcomemessagepath.exists())
			{
				welcomemessagepath.createNewFile();
				FileOutputStream fos1= new FileOutputStream(welcomemessagepath);
				ObjectOutputStream oos1= new ObjectOutputStream(fos1);
				oos1.writeObject(showwelcomemessage);
				oos1.close();
				fos1.close();
			}
			FileInputStream input1= new FileInputStream(welcomemessagepath);
			ObjectInputStream object1 = new ObjectInputStream(input1);
			showwelcomemessage = (boolean) object1.readObject();
			object1.close();
			input1.close();
		}catch(IOException exception) {exception.printStackTrace(); return;} catch (ClassNotFoundException e) {e.printStackTrace();}
		if (showwelcomemessage)
		{
			JFrame frame = new JFrame();
			Object stringArray[] = {"Continue","Don't Show Again"};
			showwelcomemessage = (JOptionPane.showOptionDialog(frame, "Please start by selecting an existing team or creating a new one.\nYou can always access the \"Help\" button at the top of the screen.", "Welcome to Team Manager",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,stringArray,stringArray[0])==JOptionPane.YES_OPTION?true:false);  
			try
			{
				File welcomemessagepath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","welcomemessage.ser");
				FileOutputStream fos1= new FileOutputStream(welcomemessagepath);
				ObjectOutputStream oos1= new ObjectOutputStream(fos1);
				oos1.writeObject(showwelcomemessage);
				oos1.close();
				fos1.close();
				File teamlistpath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","teamlist.ser");
				if(!teamlistpath.exists())
				{
					teamlistpath.createNewFile();
					FileOutputStream fos2= new FileOutputStream(teamlistpath);
					ObjectOutputStream oos2= new ObjectOutputStream(fos2);
					ArrayList<Team> teamlist = new ArrayList<Team>();
					oos2.writeObject(teamlist);
					oos2.close();
					fos2.close();
				}
				FileInputStream input = new FileInputStream(teamlistpath);
				ObjectInputStream object = new ObjectInputStream(input);
				teamlist = (ArrayList<Team>) object.readObject();
				object.close();
				input.close();
			}catch(IOException exception) {exception.printStackTrace(); return;} catch (ClassNotFoundException e) {e.printStackTrace();}
			frame = new TeamManager();
			frame.setVisible(true);
			frame.setTitle("Team Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		else
		{
			try
			{
				File welcomemessagepath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","welcomemessage.ser");
				FileOutputStream fos1= new FileOutputStream(welcomemessagepath);
				ObjectOutputStream oos1= new ObjectOutputStream(fos1);
				oos1.writeObject(showwelcomemessage);
				oos1.close();
				fos1.close();
				File teamlistpath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","teamlist.ser");
				if(!teamlistpath.exists())
					teamlistpath.createNewFile();
				FileInputStream input = new FileInputStream(teamlistpath);
				ObjectInputStream object = new ObjectInputStream(input);
				teamlist = (ArrayList<Team>) object.readObject();
				object.close();
				input.close();
			}catch(IOException exception) {exception.printStackTrace(); return;} catch (ClassNotFoundException e) {e.printStackTrace();}
			JFrame frame = new TeamManager();
			frame.setTitle("Team Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
	}
	public TeamManager()
	{
		createMainView();
		createBiggestPanel();
		createTitlePanel();
		biggestPanel.add(titlePanel, BorderLayout.NORTH);
		biggestPanel.add(mainview, BorderLayout.CENTER);
		add(biggestPanel);
		setBounds(100, 100, 450, 300);
	}
	/**
	 * @purpose - Creates the title panel, which holds the help and quit buttons and the title. It always resides at the top of the screen.
	 */
	private void createTitlePanel()
	{
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		JLabel title = new JLabel("Welcome to Team Manager");
		title.setFont(new Font(Font.SERIF, 0, 20));
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					File teamlistpath = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "TeamManager","teamlist.ser");
					FileOutputStream fos= new FileOutputStream(teamlistpath);
					ObjectOutputStream oos= new ObjectOutputStream(fos);
					oos.writeObject(teamlist);
					oos.close();
					fos.close();
				}catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				System.exit(0);
			}
		});
		JButton help = new JButton("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Welcome to Team Manager. To begin, please select a team or create a new one.\nIn each team, you can add players. These players can then be graded on criterion.\nThe results of matches between players can also be recorded.\nFinally, you can view rankings of players and create a team from players.");
			}
		});
		titlePanel.add(help);
		titlePanel.add(title);
		titlePanel.add(btnQuit);
	}
	/**
	 * @purpose - Creates the biggest panel, which holds all other panels. It is always on the screen.
	 */
	private void createBiggestPanel()
	{
		biggestPanel = new JPanel();
		biggestPanel.setLayout(new BorderLayout(0,0));
	}
	/**
	 * @purpose Creates the main view, which contains all the team options as well as the add and remove team options.
	 */
	private void createMainView()
	{
		mainview = new JPanel();
		mainview.setBounds(100, 100, 450, 300);
		mainview.setLayout(new GridLayout(teamlist.size()+1,1));
		for (Team curteam:teamlist)
		{
			JButton teambutton = new JButton(curteam.name);
			teambutton.setFont(new Font("Al Bayan", Font.PLAIN, 20));
			teambutton.setHorizontalAlignment(SwingConstants.LEFT);
			teambutton.setAlignmentX(Component.CENTER_ALIGNMENT);
			teambutton.setMargin(new Insets(1,1,1,1));
			teambutton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					createTeamScreen(curteam);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(teamview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			});
			mainview.add(teambutton);
		}		

		JButton addSport = new JButton("Add Sport");
		addSport.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createTeamCreator();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamCreator,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		addSport.setBounds(83, 140, 113, 35);
		addSport.setFont(new Font("Al Bayan", Font.PLAIN, 20));
		addSport.setHorizontalAlignment(SwingConstants.LEFT);
		addSport.setAlignmentX(Component.CENTER_ALIGNMENT);
		addSport.setMargin(new Insets(1,1,1,1));  
		mainview.add(addSport);

		JButton removeSport = new JButton("Remove Sport");
		removeSport.setBounds(208, 140, 141, 35);
		removeSport.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createRemoveTeam();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(removePanel,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});

		removeSport.setFont(new Font("Al Bayan", Font.PLAIN, 20));
		removeSport.setHorizontalAlignment(SwingConstants.LEFT);
		removeSport.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeSport.setMargin(new Insets(1,1,1,1));  
		mainview.add(removeSport); 
	}
	/**
	 * @purpose Creates the criterion view, which displays all the criterion as well as their values.
	 * @param toCreate - the team for which the criterion view should be created.
	 */
	private void createCriterionView(Team toCreate)
	{
		criterionview= new JPanel();
		criterionview.setBounds(6, 53, 438, 219);
		criterionview.setLayout(null);

		JLabel lblEnterTeamName = new JLabel("Criterion:");
		lblEnterTeamName.setBounds(6, 6, 144, 16);
		criterionview.add(lblEnterTeamName);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(6, 184, 68, 29);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createCriterionAdd(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(addcriterion,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		criterionview.add(btnNewButton);

		JButton lblAddCriterionTo = new JButton("Back");
		lblAddCriterionTo.setBounds(378, -2, 54, 35);
		lblAddCriterionTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				createTeamScreen(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		criterionview.add(lblAddCriterionTo);

		EventList<Criterion> unsortedcriterionlist = new BasicEventList<Criterion>();
		class Critcomparator implements Comparator<Criterion> {
			public int compare(Criterion crit1, Criterion crit2) {
				return crit1.getCategory().compareTo(crit2.getCategory());
			}
		}
		SortedList<Criterion> criterionlist = new SortedList<Criterion>(unsortedcriterionlist, new Critcomparator());
		for (Criterion curcrit:toCreate.criterionlist)
			criterionlist.add(curcrit);
		class CriterionTableFormat implements TableFormat<Criterion> {
			public int getColumnCount() {
				if (toCreate.type==0)
					return 7;
				else
					return 5;
			}
			public String getColumnName(int column) {
				if (toCreate.type==0)
				{
					if(column == 0)      return "Name";
					else if(column == 1) return "Cat.";
					else if(column == 2) return "Mean";
					else if(column == 3) return "BV";
					else if(column == 4) return "BJV";
					else if(column == 5) return "GV";
					else if(column == 6) return "GJV";
					throw new IllegalStateException();
				}
				else
				{
					if(column == 0)      return "Name";
					else if(column == 1) return "Cat.";
					else if(column == 2) return "Mean";
					else if(column == 3) return "BV";
					else if(column == 4) return "GV";
					throw new IllegalStateException();
				}
			}
			public Object getColumnValue(Criterion crit, int column) {
				if (toCreate.type==0)
				{
					if(column == 0)      return crit.getName();
					else if(column == 1) return crit.getCategory();
					else if(column == 2)
					{
						if (toCreate.playerlist.size()==0)
							return 0;
						int sum = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getid()==(crit.getid()))
								location = i;
						for (Player curplayer:toCreate.playerlist)
							sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
						sum = sum/toCreate.playerlist.size();
						return sum;
					}
					else if(column == 3)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==0)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}
					else if(column == 4)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==1)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}
					else if(column == 5)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==2)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}
					else if(column == 6)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==3)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}
					throw new IllegalStateException();
				}
				else
				{
					if(column == 0)      return crit.getName();
					else if(column == 1) return crit.getCategory();
					else if(column == 2)
					{
						if (toCreate.playerlist.size()==0)
							return 0;
						int sum = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getid()==(crit.getid()))
								location = i;
						for (Player curplayer:toCreate.playerlist)
							sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
						sum = sum/toCreate.playerlist.size();
						return sum;
					}
					else if(column == 3)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==0)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}
					else if(column == 4)
					{
						int sum = 0;
						int todivide = 0;
						int location = 0;
						for (int i = 0; i<toCreate.criterionlist.size(); i++)
							if(toCreate.criterionlist.get(i).getName().equals(crit.getName()))
								location = i; 
						for (Player curplayer:toCreate.playerlist)
							if (curplayer.quartile==3)
							{
								sum+=curplayer.criterionhistory.get(curplayer.criterionhistory.size()-1).get(location);
								todivide++;
							}
						if (todivide==0)
							return 0;
						sum = sum/todivide;
						return sum;
					}

					throw new IllegalStateException();
				}

			}
		}
		class CritFilterator implements TextFilterator<Criterion> {
			public void getFilterStrings(java.util.List<String> baseList, Criterion crit) {
				baseList.add(crit.getName());
				baseList.add(crit.getCategory());
			}

		}
		JTextField filterEdit = new JTextField(10);
		MatcherEditor<Criterion> textMatcherEditor = new TextComponentMatcherEditor<Criterion>(filterEdit, new CritFilterator());
		FilterList<Criterion> textFilteredIssues = new FilterList<Criterion>(criterionlist, textMatcherEditor);

		AdvancedTableModel<Criterion> critTableModel =
				GlazedListsSwing.eventTableModelWithThreadProxyList(textFilteredIssues,new CriterionTableFormat());
		JTable critlist = new JTable(critTableModel);
		JScrollPane critscroller = new JScrollPane(critlist);
		critscroller.setBounds(10, 30, 370, 150);

		JButton btnSendReport = new JButton("Remove");
		btnSendReport.setHorizontalAlignment(SwingConstants.LEFT);
		btnSendReport.setBounds(178, 184, 74, 29);
		btnSendReport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (critlist.getSelectedRow()!=-1)
				{
					int location = criterionlist.get(critlist.convertRowIndexToModel(critlist.getSelectedRow())).getid();
					toCreate.criterionlist.remove(location);
					for (Player curplayer:toCreate.playerlist)
						for (ArrayList<Integer> criterionvalues:curplayer.criterionhistory)
							criterionvalues.remove(location);
					for (int i=0; i<toCreate.criterionlist.size();i++)
						toCreate.criterionlist.get(i).setid(i);
					createCriterionView(toCreate);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(criterionview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		criterionview.add(btnSendReport);

		JButton btnDone = new JButton("Back");
		btnDone.setBounds(-3, 30, 77, 29);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
		btnEdit.setBounds(345, 184, 74, 29);
		btnEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				if (critlist.getSelectedRow()!=-1)
				{
					criterionmodify(toCreate,criterionlist.get(critlist.convertRowIndexToModel(critlist.getSelectedRow())));
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(addcriterion,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		criterionview.add(btnEdit);

		criterionview.add(new JLabel("Criterion Name: "));
		criterionview.add(filterEdit);
		criterionview.add(critscroller);
	}
	/**
	 * @purpose Creates the team creator panel, which takes the user input and creates a new team.
	 */
	private void createTeamCreator()
	{
		teamCreator= new JPanel();
		teamCreator.setLayout(new GridLayout(3,1));
		teamCreator.setBounds(100, 100, 450, 300);
		JLabel lblEnterTeamName = new JLabel("Enter Team Name");
		teamCreator.add(lblEnterTeamName);

		JLabel lblSelectTeamType = new JLabel("Select Team Type");
		lblSelectTeamType.setHorizontalAlignment(SwingConstants.LEFT);
		teamCreator.add(lblSelectTeamType);


		JTextField txtEgBadmintonS = new JTextField();
		txtEgBadmintonS.setText("e.g. Badminton S2");
		teamCreator.add(txtEgBadmintonS);
		txtEgBadmintonS.setColumns(10);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"Badminton", "Tennis"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		list.setVisibleRowCount(2);
		list.setValueIsAdjusting(true);
		teamCreator.add(list);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				createMainView();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(mainview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamCreator.add(btnCancel);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Team justcreated = new Team(txtEgBadmintonS.getText(), new ArrayList<Player>(), list.getSelectedIndex()==0?4:6, list.getSelectedIndex(), true);
				teamlist.add(justcreated);
				createMainView();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(mainview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamCreator.add(btnDone);
		teamCreator.setVisible(true);
		teamCreator.repaint();
	}
	/**
	 * @purpose Creates the team remove screen, which displays a list of teams and allows the user to remove any.
	 */
	private void createRemoveTeam()
	{
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() 
				{
			private static final long serialVersionUID = 2331L;
			public int getSize() 
			{
				return teamlist.size();
			}
			public String getElementAt(int index) 
			{
				return teamlist.get(index).name;
			}
				});
		removePanel = new JPanel();
		removePanel.setLayout(new GridLayout(3,1));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(16, 34, 200, 130);
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(6, 300, 95, 29);
		btnBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createMainView();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(mainview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		removePanel.add(btnBack);
		removePanel.add(scrollPane);
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(105, 167, 95, 29);
		btnRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!list.isSelectionEmpty())
				{
					teamlist.remove(list.getSelectedIndex());
					createRemoveTeam();
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(removePanel,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		removePanel.add(btnRemove);
	}
	/**
	 * @purpose Creates the team view screen, which shows all important functions of a team.
	 * @param toCreate - the team whose screen is created.
	 */
	private void createTeamScreen (Team toCreate)
	{
		teamview = new JPanel();
		teamview.setBounds(6, 53, 438, 219);
		teamview.setLayout(null);

		JButton lblSelectTeamType = new JButton("View Games");
		lblSelectTeamType.setBounds(256, 59, 176, 54);
		lblSelectTeamType.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createGames(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(gamepanel,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamview.add(lblSelectTeamType);


		JLabel lblEnterTeamName = new JLabel("Players:");
		lblEnterTeamName.setBounds(6, 6, 144, 16);
		teamview.add(lblEnterTeamName);

		JButton lblAddCriterionTo = new JButton("Manage Criterion");
		lblAddCriterionTo.setBounds(256, 6, 176, 54);
		lblAddCriterionTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createCriterionView(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(criterionview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamview.add(lblAddCriterionTo);
		JButton btnManageTeam = new JButton("Manage Team");
		btnManageTeam.setBounds(256, 117, 176, 54);
		btnManageTeam.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createRoster(toCreate);
				biggestPanel.removeAll();
				setBounds(100,100,450,450);
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(rosterpanel,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamview.add(btnManageTeam);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(16, 190, 198, 29);
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				createMainView();
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(mainview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamview.add(btnBack);

		JButton btnDone = new JButton("Back");
		btnDone.setBounds(-3, 30, 77, 29);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() 
				{
			private static final long serialVersionUID = 13434L;
			public int getSize() 
			{
				return toCreate.playerlist.size();
			}
			public String getElementAt(int index) 
			{
				return toCreate.playerlist.get(index).getFirstName() + " " + toCreate.playerlist.get(index).getLastName();
			}
				});

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(16, 34, 200, 130);
		teamview.add(scrollPane);

		JButton btnAdd = new JButton("Edit");
		btnAdd.setBounds(6, 167, 95, 29);
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!list.isSelectionEmpty())
				{
					createPlayerView(toCreate.playerlist.get(list.getSelectedIndex()),toCreate.criterionlist);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(playerview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		teamview.add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(105, 167, 95, 29);
		btnRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!list.isSelectionEmpty())
				{
					Player toremove = toCreate.playerlist.get(list.getSelectedIndex());
					toCreate.playerlist.remove(toremove);
					if(toremove.getGender())
					{
						toCreate.jvboys.remove(toremove);
						toCreate.vboys.remove(toremove);
					}
					else
					{
						toCreate.jvgirls.remove(toremove);
						toCreate.vgirls.remove(toremove);
					}
					createTeamScreen(toCreate);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(teamview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		teamview.add(btnRemove);

		JButton btnAddNewPlayer = new JButton("Add New Player");
		btnAddNewPlayer.setBounds(256, 173, 176, 46);
		btnAddNewPlayer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				playeraddcreator(toCreate);
				biggestPanel.removeAll();
				setBounds(100, 100, 500, 370);
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(playeradd,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		teamview.add(btnAddNewPlayer);
	}
	/**
	 * @purpose Creates the player add creator view, which allows the user to create a new player.
	 * @param sentfrom - the team onto which the created player should be added.
	 */
	private void playeraddcreator(Team sentfrom)
	{
		playeradd = new JPanel();
		playeradd.setBounds(6, 71, 438, 300);
		playeradd.setLayout(null);
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 55361L;
			String[] values = new String[] {"Male", "Female"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		list.setVisibleRowCount(2);
		list.setValueIsAdjusting(true);
		list.setBounds(200, 132, 200, 47);
		playeradd.add(list);

		JLabel lblGender = new JLabel("Gender:");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGender.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblGender.setAlignmentX(0.5f);
		lblGender.setBounds(67, 136, 119, 35);
		playeradd.add(lblGender);

		JTextField txtEgypandeaesacin = new JTextField();
		txtEgypandeaesacin.setText("16ypande@aes.ac.in");
		txtEgypandeaesacin.setColumns(10);
		txtEgypandeaesacin.setBounds(192, 182, 215, 35);
		playeradd.add(txtEgypandeaesacin);

		JLabel lblAddNewSport = new JLabel("Yash Pande is JV and Varsity Eligible.");
		lblAddNewSport.setBounds(54, 229, 348, 35);
		lblAddNewSport.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblAddNewSport.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewSport.setAlignmentX(Component.CENTER_ALIGNMENT);
		playeradd.add(lblAddNewSport);

		JLabel lblBadmintonSeason = new JLabel("Player Name: ");
		lblBadmintonSeason.setBounds(0, 16, 186, 35);
		lblBadmintonSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblBadmintonSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBadmintonSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		playeradd.add(lblBadmintonSeason);

		JLabel lblTennisSeason = new JLabel("Age:");
		lblTennisSeason.setBounds(30, 52, 156, 35);
		lblTennisSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblTennisSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTennisSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		playeradd.add(lblTennisSeason);

		JLabel lblElementarySchoolPeriod = new JLabel("Grade:");
		lblElementarySchoolPeriod.setBounds(67, 89, 119, 35);
		lblElementarySchoolPeriod.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod.setAlignmentX(Component.CENTER_ALIGNMENT);
		playeradd.add(lblElementarySchoolPeriod);
		JLabel lblElementarySchoolPeriod_1 = new JLabel("Email Address:");
		lblElementarySchoolPeriod_1.setBounds(67, 181, 119, 35);
		lblElementarySchoolPeriod_1.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		playeradd.add(lblElementarySchoolPeriod_1);
		JTextField txtEnterGradeAs = new JTextField(2);
		JTextField txtEnterAgeHere = new JTextField(2);
		JTextField txtEnterNameHere = new JTextField();
		txtEnterNameHere.setText("Yash Pande");
		txtEnterNameHere.setBounds(192, 16, 215, 35);
		txtEnterNameHere.setColumns(10);
		txtEnterNameHere.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void removeUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void insertUpdate(DocumentEvent e) {				
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
		});
		playeradd.add(txtEnterNameHere);
		txtEnterAgeHere.setText("16");
		txtEnterAgeHere.setColumns(10);
		txtEnterAgeHere.setBounds(192, 53, 215, 35);
		txtEnterAgeHere.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void removeUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void insertUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
		});
		playeradd.add(txtEnterAgeHere);
		txtEnterGradeAs.setText("11");
		txtEnterGradeAs.setColumns(10);
		txtEnterGradeAs.setBounds(192, 90, 215, 35);
		txtEnterGradeAs.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void removeUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
			public void insertUpdate(DocumentEvent e) {
				String name = txtEnterNameHere.getText();
				String grade = txtEnterGradeAs.getText();
				String age = txtEnterAgeHere.getText();
				if (grade.matches("^\\d+$")&&age.matches("^\\d+$")&&grade.length()<3&&age.length()<3)
					lblAddNewSport.setText(name + " is " + (Integer.parseInt(age)<=16?"JV ":"") + (Integer.parseInt(age)<=16&&Integer.parseInt(grade)>=9?"and ":"") + (!(Integer.parseInt(age)<=16||Integer.parseInt(grade)>=9)?"Not ":"") + (Integer.parseInt(grade)>=9?"Varsity Eligible.":"Eligible"));;
			}
		});
		playeradd.add(txtEnterGradeAs);
		JButton btnOkay = new JButton("Okay");
		btnOkay.setBounds(315, 265, 117, 29);
		btnOkay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (txtEnterGradeAs.getText().matches("^\\d+$")&&txtEnterAgeHere.getText().matches("^\\d+$")&&txtEnterGradeAs.getText().length()<3&&txtEnterAgeHere.getText().length()<3)
				{
					Player addedplayer = new Player();
					addedplayer.setJVelgible(Integer.parseInt(txtEnterAgeHere.getText())<=16);
					addedplayer.setVeligible(Integer.parseInt(txtEnterGradeAs.getText())>=9);
					String[] splited = txtEnterNameHere.getText().split("\\s+");
					addedplayer.setFirstName(splited[0]);
					addedplayer.setLastName(splited[splited.length-1]);
					addedplayer.setEmailAddress(txtEgypandeaesacin.getText());
					addedplayer.setGamesWon(0);
					addedplayer.setGender(list.getSelectedIndex()==0?true:false);
					addedplayer.team=sentfrom;
					ArrayList<Integer> toadd = new ArrayList<Integer>();
					for (int i=0; i<sentfrom.criterionlist.size(); i++)
						toadd.add(0);
					addedplayer.criterionhistory.add(toadd);
					sentfrom.playerlist.add(addedplayer);
					if (sentfrom.type==0)
					{
						if (addedplayer.isJVelgible()&&addedplayer.getGender())
						{
							addedplayer.quartile=1;
							sentfrom.jvboys.add(addedplayer);
						}
						if (addedplayer.isVeligible()&&addedplayer.getGender())
						{
							addedplayer.quartile=0;
							sentfrom.vboys.add(addedplayer);
						}
						if (addedplayer.isJVelgible()&&!addedplayer.getGender())
						{
							addedplayer.quartile=2;
							sentfrom.jvgirls.add(addedplayer);
						}
						if (addedplayer.isVeligible()&&!addedplayer.getGender())
						{
							addedplayer.quartile=3;
							sentfrom.vgirls.add(addedplayer);
						}
					}
					else
					{
						if (addedplayer.getGender())
						{
							addedplayer.quartile=0;
							sentfrom.vboys.add(addedplayer);
						}
						if (!addedplayer.getGender())
						{
							addedplayer.quartile=1;
							sentfrom.vgirls.add(addedplayer);
						}
					}
					createTeamScreen(sentfrom);
					biggestPanel.removeAll();
					setBounds(100,100,450,300);
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(teamview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
				else
					JOptionPane.showMessageDialog(playeradd, "Player data not entered correctly.\nPlease enter grades and ages as integers (e.g. 3).");
			}
		});
		playeradd.add(btnOkay);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(25, 265, 117, 29);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createTeamScreen(sentfrom);
				biggestPanel.removeAll();
				setBounds(100,100,450,300);
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		playeradd.add(btnCancel);
	}
	/**
	 * @purpose Creates the roster view, and sorts the players into the correct teams.
	 * @param toCreate - the team for which the roster view should be created.
	 */
	private void createRoster (Team toCreate)
	{
		class playercompare implements Comparator<Player> {
			public int compare(Player p2, Player p1) {
				p1.calculateRating();
				p2.calculateRating();
				if (p1.getRating()!=p2.getRating())
					return p1.getRating()-(p2.getRating());
				else
				{
					if (p1.getGamesPlayed()==0||p2.getGamesPlayed()==0)
						return p1.getGamesPlayed()-p2.getGamesPlayed();
					if (p1.getGamesWon()/p1.getGamesPlayed()!=p2.getGamesWon()/p2.getGamesPlayed())
						return 1000*p1.getGamesWon()/p1.getGamesPlayed()-1000*p2.getGamesWon()/p2.getGamesPlayed(); //Numbers are multiplied by 1000 because otherwise the ratio is less than one which evaluates to zero when converting to an integer.
					else
					{
						ArrayList<Game> p1games = new ArrayList<Game>();
						for (Game playgame:toCreate.gamelist)
							if (playgame.getTeam1().contains(p1)||playgame.getTeam2().contains(p1))
								p1games.add(playgame);
						ArrayList<Game> p2games = new ArrayList<Game>();
						for (Game playgame:toCreate.gamelist)
							if (playgame.getTeam1().contains(p2)||playgame.getTeam2().contains(p2))
								p2games.add(playgame);
						p1games.removeAll(p1.importantgames);
						p2games.removeAll(p2.importantgames);
						int p1gamesplayed = 0;
						int p2gamesplayed = 0;
						int p1points = 0;
						int p2points = 0;
						for (int i=0; i<p1games.size(); i++)
						{
							p1gamesplayed++;
							if (p1games.get(i).getTeam1().contains(p1))
								p1points += p1games.get(i).findteampoints(1);
							else
								p1points += p1games.get(i).findteampoints(2);
						}
						for (int i=0; i<p2games.size(); i++)
						{
							p2gamesplayed++;
							if (p2games.get(i).getTeam1().contains(p1))
								p2points += p2games.get(i).findteampoints(1);
							else
								p2points += p2games.get(i).findteampoints(2);
						}
						if (p1gamesplayed==0||p2gamesplayed==0)
							return p1points-p2points;
						if (p1points/p1gamesplayed!=p2points/p2gamesplayed)
							return p1points/p1gamesplayed-p2points/p2gamesplayed;
						return p1.getGamestied()+p1.getGamesWon()-p2.getGamesWon()-p2.getGamestied();
					}
				}
			}
		}
		Comparator<Player> sorter = new playercompare();
		for (Player curplayer:toCreate.playerlist)
		{
			if (curplayer.isJVelgible()&&curplayer.getGender()&&!toCreate.jvboys.contains(curplayer))
				toCreate.jvboys.add(curplayer);
			if (curplayer.isVeligible()&&curplayer.getGender()&&!toCreate.vboys.contains(curplayer))
				toCreate.vboys.add(curplayer);
			if (curplayer.isJVelgible()&&!curplayer.getGender()&&!toCreate.jvgirls.contains(curplayer))
				toCreate.jvgirls.add(curplayer);
			if (curplayer.isVeligible()&&!curplayer.getGender()&&!toCreate.vgirls.contains(curplayer))
				toCreate.vgirls.add(curplayer);
		}
		toCreate.jvboys.sort(sorter);
		toCreate.jvgirls.sort(sorter);
		toCreate.vboys.sort(sorter);
		toCreate.vgirls.sort(sorter);
		if (toCreate.type==0)
		{
			if (toCreate.jvboys.size()>0&&toCreate.vboys.size()>0)
			{
				for (int i=0; i<toCreate.jvboys.size(); i++)
					for (int j=toCreate.vboys.size()-1; j>=0; j--)
					{
						if (i>=0&&j<toCreate.vboys.size())
						{
							if (toCreate.jvboys.get(i).equals(toCreate.vboys.get(j)))
							{
								if (toCreate.vboys.size()<=5)
								{
									toCreate.jvboys.remove(i);
									i--;
								}
								else if (toCreate.jvboys.size()<=5)
									toCreate.vboys.remove(j);
								else if (j==0||j==1)
								{
									toCreate.jvboys.remove(i);
									i--;
								}
								else if (j>=2&&j<=5)
								{
									if (i==0||i==1)
									{
										if (toCreate.jvboys.get(i).getSinglesrating()>toCreate.jvboys.get(i).getDoublesrating())
											toCreate.vboys.remove(j);
										else
										{
											toCreate.jvboys.remove(i);
											i--;
										}
									}
									else
									{
										toCreate.jvboys.remove(i);
										i--;
									}
								}
								else
									toCreate.vboys.remove(j);
							}
						}
					}
			}
			if (toCreate.vgirls.size()>0&&toCreate.jvgirls.size()>0)
			{
				for (int i=0; i<toCreate.jvgirls.size(); i++)
					for (int j=toCreate.vgirls.size()-1; j>=0; j--)
						if (i>=0&&j<toCreate.vgirls.size())
						{
							if (toCreate.jvgirls.get(i).equals(toCreate.vgirls.get(j)))
							{
								if (toCreate.vgirls.size()<=5)
								{
									toCreate.jvgirls.remove(i);
									i--;
								}
								else if (toCreate.jvgirls.size()<=5)
									toCreate.vgirls.remove(j);
								else if (j==0||j==1)
								{
									toCreate.jvgirls.remove(i);
									i--;
								}
								else if (j>=2&&j<=5)
								{
									if (i==0||i==1)
									{
										if (toCreate.jvgirls.get(i).getSinglesrating()>toCreate.jvgirls.get(i).getDoublesrating())
										{
											toCreate.vgirls.remove(j);
											j++;
										}
										else
										{
											toCreate.jvgirls.remove(i);
											i--;
										}
									}
									else
									{
										if (i>=0)
										{
											toCreate.jvgirls.remove(i);
											i--;
										}
									}
								}
								else
								{
									toCreate.vgirls.remove(j);
									j--;
								}
							}
						}
			}	
			while (toCreate.jvboys.size()>5)
				toCreate.jvboys.remove(toCreate.jvboys.size()-1);
			while (toCreate.vboys.size()>5)
				toCreate.vboys.remove(toCreate.jvboys.size()-1);
			while (toCreate.jvgirls.size()>5)
				toCreate.jvgirls.remove(toCreate.jvboys.size()-1);
			while (toCreate.vgirls.size()>5)
				toCreate.vgirls.remove(toCreate.jvboys.size()-1);
			if (toCreate.vboys.size()>3)
			{
				if (toCreate.vboys.get(1).getDoublesrating()>toCreate.vboys.get(2).getDoublesrating()&&toCreate.vboys.get(1).getSinglesrating()<toCreate.vboys.get(2).getSinglesrating())
				{
					Player templayer = toCreate.vboys.get(1);
					toCreate.vboys.set(1,toCreate.vboys.get(2));
					toCreate.vboys.set(2, templayer);
				}
			}
			if (toCreate.vgirls.size()>3)
			{
				if (toCreate.vgirls.get(1).getDoublesrating()>toCreate.vgirls.get(2).getDoublesrating()&&toCreate.vgirls.get(1).getSinglesrating()<toCreate.vgirls.get(2).getSinglesrating())
				{
					Player templayer = toCreate.vgirls.get(1);
					toCreate.vgirls.set(1,toCreate.vgirls.get(2));
					toCreate.vgirls.set(2, templayer);
				}
			}
			if (toCreate.jvboys.size()>3)
			{

				if (toCreate.jvboys.get(1).getDoublesrating()>toCreate.jvboys.get(2).getDoublesrating()&&toCreate.jvboys.get(1).getSinglesrating()<toCreate.jvboys.get(2).getSinglesrating())
				{
					Player templayer = toCreate.jvboys.get(1);
					toCreate.jvboys.set(1,toCreate.jvboys.get(2));
					toCreate.jvboys.set(2, templayer);
				}
			}
			if (toCreate.jvgirls.size()>3)
			{

				if (toCreate.jvgirls.get(1).getDoublesrating()>toCreate.jvgirls.get(2).getDoublesrating()&&toCreate.jvgirls.get(1).getSinglesrating()<toCreate.jvgirls.get(2).getSinglesrating())
				{
					Player templayer = toCreate.jvgirls.get(1);
					toCreate.jvgirls.set(1,toCreate.jvgirls.get(2));
					toCreate.jvgirls.set(2, templayer);
				}
			}
			for (Player curplayer:toCreate.jvboys)
				curplayer.quartile=1; //BV = 0. BJV = 1. GV = 2, GJV = 3
			for (Player curplayer:toCreate.vboys)
				curplayer.quartile=0; //BV = 0. BJV = 1. GV = 2, GJV = 3
			for (Player curplayer:toCreate.jvgirls)
				curplayer.quartile=2; //BV = 0. BJV = 1. GV = 2, GJV = 3
			for (Player curplayer:toCreate.vgirls)
				curplayer.quartile=3; //BV = 0. BJV = 1. GV = 2, GJV = 3
		}
		if (toCreate.type==1)
		{
			toCreate.vboys.sort(sorter);
			toCreate.vgirls.sort(sorter);
			while (toCreate.vboys.size()>10)
				toCreate.vboys.remove(toCreate.vboys.size()-1);
			while (toCreate.vgirls.size()>10)
				toCreate.vgirls.remove(toCreate.vgirls.size()-1);
			if (toCreate.vboys.size()>4)
			{
				if (toCreate.vboys.get(2).getDoublesrating()>toCreate.vboys.get(3).getDoublesrating()&&toCreate.vboys.get(2).getSinglesrating()<toCreate.vboys.get(3).getSinglesrating())
				{
					Player templayer = toCreate.vboys.get(2);
					toCreate.vboys.set(2,toCreate.vboys.get(3));
					toCreate.vboys.set(3, templayer);
				}
			}
			if (toCreate.vgirls.size()>4)
			{
				if (toCreate.vgirls.get(2).getDoublesrating()>toCreate.vgirls.get(3).getDoublesrating()&&toCreate.vgirls.get(2).getSinglesrating()<toCreate.vgirls.get(3).getSinglesrating())
				{
					Player templayer = toCreate.vgirls.get(2);
					toCreate.vgirls.set(2,toCreate.vgirls.get(3));
					toCreate.vgirls.set(3, templayer);
				}
			}
			for (Player curplayer:toCreate.vboys)
				curplayer.quartile=0; //BV = 0. BJV = 1. GV = 2, GJV = 3
			for (Player curplayer:toCreate.vgirls)
				curplayer.quartile=3; //BV = 0. BJV = 1. GV = 2, GJV = 3
		}
		rosterpanel = new JPanel();
		rosterpanel.setBounds(6, 53, 400, 300);
		rosterpanel.setLayout(new BorderLayout());
		JPanel topbar = new JPanel();
		topbar.setLayout(new FlowLayout());
		JButton backbutton = new JButton("Back");
		backbutton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createTeamScreen(toCreate);
				biggestPanel.removeAll();
				setBounds(100,100,450,300);
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		backbutton.setAlignmentX(RIGHT_ALIGNMENT);
		topbar.add(backbutton);
		rosterpanel.add(topbar, BorderLayout.NORTH);
		JLabel lblEnterTeamName = new JLabel("Team:");
		lblEnterTeamName.setBounds(6, 6, 144, 16);
		rosterpanel.add(lblEnterTeamName);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = -9052830358324323458L;
			public int getSize() {
				return toCreate.vboys.size();
			}
			public String getElementAt(int index) {
				return toCreate.vboys.get(index).getFirstName()+" " +toCreate.vboys.get(index).getLastName();
			}
		});

		JList<String> list1 = new JList<String>();
		list1.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 9358324323458L;
			public int getSize() {
				return toCreate.jvboys.size();
			}
			public String getElementAt(int index) {
				return toCreate.jvboys.get(index).getFirstName()+" " +toCreate.jvboys.get(index).getLastName();
			}
		});

		JList<String> list2 = new JList<String>();
		list2.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = -458324323458L;
			public int getSize() {
				return toCreate.vgirls.size();
			}
			public String getElementAt(int index) {
				return toCreate.vgirls.get(index).getFirstName()+" " +toCreate.vgirls.get(index).getLastName();
			}
		});

		JList<String> list3 = new JList<String>();
		list3.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 354524323458L;
			public int getSize() {
				return toCreate.jvgirls.size();
			}
			public String getElementAt(int index) {
				return toCreate.jvgirls.get(index).getFirstName()+" " +toCreate.jvgirls.get(index).getLastName();
			}
		});
		JPanel centerpanel = new JPanel();
		centerpanel.setLayout(new GridLayout(2,2));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(16, 34, 182, 151);
		centerpanel.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane(list1);
		scrollPane_1.setBounds(232, 34, 182, 151);
		if (toCreate.type!=1)
			centerpanel.add(scrollPane_1);

		JScrollPane scrollPane_2 = new JScrollPane(list2);
		scrollPane_2.setBounds(232, 224, 182, 151);
		centerpanel.add(scrollPane_2);

		JScrollPane scrollPane_3 = new JScrollPane(list3);

		JLabel lblJvGirls = new JLabel("Varsity Girls");
		lblJvGirls.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_2.setColumnHeaderView(lblJvGirls);

		JLabel lblJvBoys = new JLabel("JV Boys");
		lblJvBoys.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblJvBoys);

		JLabel lblVarsityBoys = new JLabel("Varsity Boys");
		lblVarsityBoys.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblVarsityBoys);
		scrollPane.setBounds(16, 224, 182, 151);
		centerpanel.add(scrollPane);

		JLabel lblVarsityGirls = new JLabel("JV Girls");
		lblVarsityGirls.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_3.setColumnHeaderView(lblVarsityGirls);
		rosterpanel.add(centerpanel,BorderLayout.CENTER);
		if (toCreate.type!=1)
			centerpanel.add(scrollPane_3);
	}
	/**
	 * @purpose Creates the games view, which displays all the games that players should play.
	 * @param toCreate - the team for which the games view should be created.
	 */
	private void createGames (Team toCreate)
	{
		gamepanel = new JPanel();
		gamepanel.setBounds(6, 53, 977, 419);
		gamepanel.setLayout(new BorderLayout());
		JPanel topbar = new JPanel();
		topbar.setLayout(new FlowLayout());
		JButton backbutton = new JButton("Back");
		backbutton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createTeamScreen(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		topbar.add(backbutton);
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createGames(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(gamepanel,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		JButton setcourts = new JButton("Set Court Number");
		setcourts.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				JPanel optionpanel = new JPanel();
				JTextField courtentry = new JTextField(2);
				courtentry.setText("#");
				optionpanel.add(courtentry);
				int selection = JOptionPane.showConfirmDialog(gamepanel ,optionpanel, "Edit Court Number", JOptionPane.OK_CANCEL_OPTION);
				if (selection == JOptionPane.OK_OPTION)
				{
					if (courtentry.getText().matches("^\\d+$"))
						toCreate.numCourts=Integer.parseInt(courtentry.getText());
					else
					{
						JOptionPane.showMessageDialog(gamepanel, "Court number not entered correctly.\nPlease enter court number as an integer (e.g. 5).");
					}
				}
				createGames(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(gamepanel,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		topbar.add(setcourts);
		gamepanel.add(topbar, BorderLayout.NORTH);
		JPanel newGamePanel = new JPanel();
		newGamePanel.setLayout(new GridLayout(2,(toCreate.numCourts+1)/2));
		class playercompare implements Comparator<Player> {
			public int compare(Player p1, Player p2) {
				p1.calculateRating();
				p2.calculateRating();
				return p1.getRating()-(p2.getRating());
			}
		}
		@SuppressWarnings("unchecked")
		ArrayList<Player> templist = (ArrayList<Player>) toCreate.playerlist.clone();
		templist.sort(new playercompare());
		for(int i=0; i<toCreate.numCourts; i++)
		{
			JPanel gamesforcourt = new JPanel();
			gamesforcourt.setLayout(new BorderLayout());
			JLabel lblCourt = new JLabel("Court " + (i+1) + ":");
			gamesforcourt.add(lblCourt,BorderLayout.NORTH);
			JList<String> listforcourt = new JList<String>();
			ArrayList<Game> curcourtlist = new ArrayList<Game>();
			ArrayList<Player> courtplayers = new ArrayList<Player>();
			int portion = 0;
			if (templist.size()/toCreate.numCourts<4)
				portion = 4;
			else
				portion = templist.size()/toCreate.numCourts;
			for (int j=0; j<portion; j++)
			{
				if (templist.size()>0)
				{
					courtplayers.add(templist.get(0));
					templist.remove(0);
				}
			}
			for (int j=0; j<courtplayers.size(); j++)
				for (int k=courtplayers.size()-1; k>=0; k--)
					if (j!=k)
					{
						ArrayList<Player> team1 = new ArrayList<Player>();
						ArrayList<Player> team2 = new ArrayList<Player>();
						team1.add(courtplayers.get(j));
						team2.add(courtplayers.get(k));
						curcourtlist.add(new Game(i,team1,team2, null, null));
					}
			listforcourt.setModel(new AbstractListModel<String>() {
				private static final long serialVersionUID = 1791934604796058454L;
				public int getSize() {
					return curcourtlist.size();
				}
				public String getElementAt(int index) {
					return curcourtlist.get(index).getname();
				}
			});
			listforcourt.addMouseListener(new MouseAdapter() {
				@SuppressWarnings("unchecked")
				public void mousePressed(MouseEvent mclick) {
					JList<String> list = (JList<String>) mclick.getSource();
					Point p = mclick.getPoint();
					int row = list.locationToIndex(p);
					if (mclick.getClickCount() == 2) {
						JPanel gameeditpanel = new JPanel();
						gameeditpanel.setLayout(new FlowLayout());
						JTextField p1score = new JTextField(12);
						p1score.setText("Team 1 Score");
						JTextField p2score = new JTextField(12);
						p2score.setText("Team 2 Score");
						gameeditpanel.add(p1score);
						gameeditpanel.add(p2score);

						int selection = JOptionPane.showConfirmDialog(gamepanel, gameeditpanel, 
								"Edit Game Results", JOptionPane.OK_CANCEL_OPTION);
						if (selection == JOptionPane.OK_OPTION)
						{
							Boolean iscorrect = true;
							for (char curchar:p1score.getText().toCharArray())
								if (!(curchar=='-'||Character.toString(curchar).matches("^\\d+$")))
									iscorrect = false;
							for (char curchar:p2score.getText().toCharArray())
								if (!(curchar=='-'||Character.toString(curchar).matches("^\\d+$")))
									iscorrect = false;
							if (iscorrect)
							{
								Game clickedgame = curcourtlist.get(row);
								clickedgame.setTeam1score(p1score.getText().toCharArray());
								clickedgame.setTeam2score(p2score.getText().toCharArray());
								if(clickedgame.findteampoints(1)>clickedgame.findteampoints(2))
								{
									for (Player curplayer:clickedgame.getTeam1())
									{
										curplayer.setGamesWon(curplayer.getGamesWon()+1);
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
									}
									for (Player curplayer:clickedgame.getTeam2())
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
								}
								if(clickedgame.findteampoints(2)>clickedgame.findteampoints(1))
								{
									for (Player curplayer:clickedgame.getTeam2())
									{
										curplayer.setGamesWon(curplayer.getGamesWon()+1);
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
									}
									for (Player curplayer:clickedgame.getTeam1())
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
								}
								if(clickedgame.findteampoints(2)==clickedgame.findteampoints(1))
								{
									for (Player curplayer:clickedgame.getTeam2())
									{
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
										curplayer.setGamestied(curplayer.getGamestied()+1);
									}
									for (Player curplayer:clickedgame.getTeam1())
									{
										curplayer.setGamesPlayed(curplayer.getGamesPlayed()+1);
										curplayer.setGamestied(curplayer.getGamestied()+1);
									}
								}
								toCreate.gamelist.add(clickedgame);
								curcourtlist.remove(clickedgame);
							}
							else
							{
								JOptionPane.showMessageDialog(gamepanel, "Game data not entered correctly.\nPlease enter data in the form [integer]-[integer]-[integer]\nFor example, 21-6-21.");
							}
						}
					}
				}
			});
			JScrollPane scrollPane = new JScrollPane(listforcourt);
			gamesforcourt.add(scrollPane, BorderLayout.CENTER);
			newGamePanel.add(gamesforcourt);
		}
		gamepanel.add(newGamePanel,BorderLayout.CENTER);
	}
	/**
	 * @purpose Creates the criterion modify panel, which allows users to modify criterion.
	 * @param toCreate - the team for which the criterion modify panel should be created.
	 * @param tomodify - the criterion that should be modified.
	 */
	private void criterionmodify (Team toCreate, Criterion tomodify)
	{
		addcriterion = new JPanel();
		addcriterion.setBounds(6, 37, 438, 235);
		addcriterion.setLayout(null);

		JLabel lblBadmintonSeason = new JLabel("Criterion Name:");
		lblBadmintonSeason.setBounds(-31, 6, 186, 35);
		lblBadmintonSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblBadmintonSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBadmintonSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblBadmintonSeason);

		JLabel lblTennisSeason = new JLabel("Overall");
		lblTennisSeason.setBounds(0, 163, 156, 35);
		lblTennisSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblTennisSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTennisSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblTennisSeason);

		JLabel lblElementarySchoolPeriod = new JLabel("Singles");
		lblElementarySchoolPeriod.setBounds(36, 94, 119, 35);
		lblElementarySchoolPeriod.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblElementarySchoolPeriod);

		JLabel lblElementarySchoolPeriod_1 = new JLabel("Doubles");
		lblElementarySchoolPeriod_1.setBounds(36, 127, 119, 35);
		lblElementarySchoolPeriod_1.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblElementarySchoolPeriod_1);

		JTextField txtEnterNameHere = new JTextField();
		txtEnterNameHere.setText(tomodify.getName());
		txtEnterNameHere.setBounds(161, 6, 215, 35);
		addcriterion.add(txtEnterNameHere);
		txtEnterNameHere.setColumns(10);

		JLabel lblAddNewSport = new JLabel("Please enter how much the criterion affects each ranking");
		lblAddNewSport.setBounds(29, 65, 403, 41);
		addcriterion.add(lblAddNewSport);
		lblAddNewSport.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblAddNewSport.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewSport.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblNewLabel = new JLabel(Integer.toString(tomodify.getEffectoverall()));
		lblNewLabel.setBounds(369, 97, 61, 27);
		addcriterion.add(lblNewLabel);

		JLabel label = new JLabel(Integer.toString(tomodify.getEffectonsingles()));
		label.setBounds(369, 130, 61, 27);
		addcriterion.add(label);

		JLabel label_1 = new JLabel(Integer.toString(tomodify.getEffectondoubles()));
		label_1.setBounds(369, 166, 61, 27);
		addcriterion.add(label_1);

		JSlider slider0 = new JSlider();
		slider0.setValue(tomodify.getEffectoverall());
		slider0.setSnapToTicks(true);
		slider0.setPaintTicks(true);
		slider0.setMinorTickSpacing(1);
		slider0.setMaximum(10);
		slider0.setBounds(167, 100, 190, 29);
		slider0.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			lblNewLabel.setText(Integer.toString(slider0.getValue()));
		}});
		addcriterion.add(slider0);

		JSlider slider_1 = new JSlider();
		slider_1.setValue(tomodify.getEffectonsingles());
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMaximum(10);
		slider_1.setBounds(167, 133, 190, 29);
		slider_1.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			label.setText(Integer.toString(slider_1.getValue()));
		}});

		addcriterion.add(slider_1);
		JSlider slider_2 = new JSlider();
		slider_2.setValue(tomodify.getEffectondoubles());
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setMinorTickSpacing(1);
		slider_2.setMaximum(10);
		slider_2.setBounds(167, 169, 190, 29);
		slider_2.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			label_1.setText(Integer.toString(slider_2.getValue()));
		}});
		addcriterion.add(slider_2);

		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(406, 3, 38, 22);

		JLabel lblCategoryName = new JLabel("Category Name:");
		lblCategoryName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategoryName.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblCategoryName.setAlignmentX(0.5f);
		lblCategoryName.setBounds(-31, 34, 186, 35);
		addcriterion.add(lblCategoryName);

		JTextField txtEgMovement = new JTextField();
		txtEgMovement.setText(tomodify.getCategory());
		txtEgMovement.setColumns(10);
		txtEgMovement.setBounds(161, 36, 215, 35);
		addcriterion.add(txtEgMovement);
		JButton btnOkay = new JButton("Okay");
		btnOkay.setBounds(315, 197, 117, 29);
		btnOkay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				tomodify.setCategory(txtEgMovement.getText());
				tomodify.setName(txtEnterNameHere.getText());
				tomodify.setEffectoverall(slider0.getValue());
				tomodify.setEffectonsingles(slider_1.getValue());
				tomodify.setEffectondoubles(slider_2.getValue());
				createCriterionView(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(criterionview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		addcriterion.add(btnOkay);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(36, 197, 117, 29);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createCriterionView(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(criterionview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		addcriterion.add(btnCancel);
	}
	/**
	 * @purpose Creates the criterion add panel, which allows users to add a criterion.
	 * @param toCreate - the team for which the criterion add panel should be created and to which the new criterion should be added.
	 */
	private void createCriterionAdd (Team toCreate)
	{
		addcriterion = new JPanel();
		addcriterion.setBounds(6, 37, 438, 235);
		addcriterion.setLayout(null);

		JLabel lblBadmintonSeason = new JLabel("Criterion Name:");
		lblBadmintonSeason.setBounds(-31, 6, 186, 35);
		lblBadmintonSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblBadmintonSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBadmintonSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblBadmintonSeason);

		JLabel lblTennisSeason = new JLabel("Overall");
		lblTennisSeason.setBounds(0, 163, 156, 35);
		lblTennisSeason.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblTennisSeason.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTennisSeason.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblTennisSeason);

		JLabel lblElementarySchoolPeriod = new JLabel("Singles");
		lblElementarySchoolPeriod.setBounds(36, 94, 119, 35);
		lblElementarySchoolPeriod.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblElementarySchoolPeriod);

		JLabel lblElementarySchoolPeriod_1 = new JLabel("Doubles");
		lblElementarySchoolPeriod_1.setBounds(36, 127, 119, 35);
		lblElementarySchoolPeriod_1.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblElementarySchoolPeriod_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElementarySchoolPeriod_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcriterion.add(lblElementarySchoolPeriod_1);

		JTextField txtEnterNameHere = new JTextField();
		txtEnterNameHere.setText("e.g. Lunge Forward");
		txtEnterNameHere.setBounds(161, 6, 215, 35);
		addcriterion.add(txtEnterNameHere);
		txtEnterNameHere.setColumns(10);

		JLabel lblAddNewSport = new JLabel("Please enter how much the criterion affects each ranking");
		lblAddNewSport.setBounds(29, 65, 403, 41);
		addcriterion.add(lblAddNewSport);
		lblAddNewSport.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblAddNewSport.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewSport.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblNewLabel = new JLabel("5");
		lblNewLabel.setBounds(369, 97, 61, 27);
		addcriterion.add(lblNewLabel);

		JLabel label = new JLabel("5");
		label.setBounds(369, 130, 61, 27);
		addcriterion.add(label);

		JLabel label_1 = new JLabel("5");
		label_1.setBounds(369, 166, 61, 27);
		addcriterion.add(label_1);

		JSlider slider0 = new JSlider();
		slider0.setValue(5);
		slider0.setSnapToTicks(true);
		slider0.setPaintTicks(true);
		slider0.setMinorTickSpacing(1);
		slider0.setMaximum(10);
		slider0.setBounds(167, 100, 190, 29);
		slider0.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			lblNewLabel.setText(Integer.toString(slider0.getValue()));
		}});
		addcriterion.add(slider0);

		JSlider slider_1 = new JSlider();
		slider_1.setValue(5);
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMaximum(10);
		slider_1.setBounds(167, 133, 190, 29);
		slider_1.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			label.setText(Integer.toString(slider_1.getValue()));
		}});

		addcriterion.add(slider_1);
		JSlider slider_2 = new JSlider();
		slider_2.setValue(5);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setMinorTickSpacing(1);
		slider_2.setMaximum(10);
		slider_2.setBounds(167, 169, 190, 29);
		slider_2.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) 
		{ 
			label_1.setText(Integer.toString(slider_2.getValue()));
		}});
		addcriterion.add(slider_2);

		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(406, 3, 38, 22);

		JLabel lblCategoryName = new JLabel("Category Name:");
		lblCategoryName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategoryName.setFont(new Font("Al Bayan", Font.PLAIN, 15));
		lblCategoryName.setAlignmentX(0.5f);
		lblCategoryName.setBounds(-31, 34, 186, 35);
		addcriterion.add(lblCategoryName);

		JTextField txtEgMovement = new JTextField();
		txtEgMovement.setText("e.g. Movement");
		txtEgMovement.setColumns(10);
		txtEgMovement.setBounds(161, 36, 215, 35);
		addcriterion.add(txtEgMovement);
		JButton btnOkay = new JButton("Okay");
		btnOkay.setBounds(315, 197, 117, 29);
		btnOkay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Criterion toadd = new Criterion();
				toadd.setid(toCreate.criterionlist.size());
				toadd.setCategory(txtEgMovement.getText());
				toadd.setName(txtEnterNameHere.getText());
				toadd.setEffectoverall(slider0.getValue());
				toadd.setEffectonsingles(slider_1.getValue());
				toadd.setEffectondoubles(slider_2.getValue());
				for (Player curplayer:toCreate.playerlist)
					for (ArrayList<Integer> ratings:curplayer.criterionhistory)
						ratings.add(0);
				toCreate.criterionlist.add(toadd);
				createCriterionView(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(criterionview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		addcriterion.add(btnOkay);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(36, 197, 117, 29);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createCriterionView(toCreate);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(criterionview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		addcriterion.add(btnCancel);

	}
	/**
	 * @purpose Creates the player view, which allows users to view a player's performance and edit their criterion values.
	 * @param toCreate - the player for which the player view should be created.
	 * @param criterionvalues - The arraylist of criterion from the player's team.
	 */
	private void createPlayerView(Player toCreate, ArrayList<Criterion> criterionvalues)
	{
		playerview = new JPanel();
		playerview.setBounds(6, 71, 438, 201);
		playerview.setLayout(null);
		JLabel lblEnterTeamName = new JLabel("Criterion:");
		lblEnterTeamName.setBounds(6, 6, 144, 16);
		playerview.add(lblEnterTeamName);

		EventList<ArrayList<Integer>> criterionlist = new BasicEventList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fullcriterion = new ArrayList<ArrayList<Integer>>();
		if (toCreate.criterionhistory.size()!=0)
			fullcriterion.addAll(toCreate.criterionhistory);
		for (ArrayList<Integer> curcrit:fullcriterion)
			criterionlist.add(curcrit);
		class CriterionTableFormat implements TableFormat<ArrayList<Integer>> {
			public int getColumnCount() {
				return criterionvalues.size();
			}
			public String getColumnName(int column) {
				if(column <= criterionvalues.size()-1)   
					return criterionvalues.get(column).getName();
				throw new IllegalStateException();
			}
			public Object getColumnValue(ArrayList<Integer> toget, int column) {
				if(column <= criterionvalues.size()-1)      
					return toget.get(column);
				throw new IllegalStateException();
			}
		}
		AdvancedTableModel<ArrayList<Integer>> critTableModel =GlazedListsSwing.eventTableModelWithThreadProxyList(criterionlist,new CriterionTableFormat());
		JTable critlist = new JTable(critTableModel);
		JScrollPane critscroller = new JScrollPane(critlist);
		critscroller.setBounds(10, 30, 370, 150);
		playerview.add(critscroller);
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(6, 184, 68, 29);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!criterionvalues.isEmpty())
				{
					JTextField[] textarray = new JTextField[criterionvalues.size()];
					playereditpanel = new JPanel();
					playereditpanel.setLayout(new FlowLayout());
					for (int i=0; i<criterionvalues.size(); i++)
					{
						JTextField ratingtoadd = new JTextField(5);
						ratingtoadd.setText(criterionvalues.get(i).getName());
						textarray[i] = ratingtoadd;
						playereditpanel.add(ratingtoadd);
					}
					int selection = JOptionPane.showConfirmDialog(playerview, playereditpanel, 
							"Edit player ratings", JOptionPane.OK_CANCEL_OPTION);
					if (selection == JOptionPane.OK_OPTION)
					{
						ArrayList<Integer> toadd = new ArrayList<Integer>();
						for (int i=0; i<criterionvalues.size(); i++)
							if (textarray[i].getText().matches("^\\d+$")&&textarray[i].getText().length()<3)
							{
								if (Integer.parseInt(textarray[i].getText())<=10&&Integer.parseInt(textarray[i].getText())>=0)
									toadd.add(Integer.parseInt(textarray[i].getText()));
								else
									toadd.add(0);
							}
							else
								toadd.add(0);
						toCreate.criterionhistory.add(toadd);
					}
				}
				createPlayerView(toCreate, criterionvalues);
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(playerview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		playerview.add(btnNewButton);

		JButton lblAddCriterionTo = new JButton("Back");
		lblAddCriterionTo.setBounds(378, -2, 54, 35);
		lblAddCriterionTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				biggestPanel.removeAll();
				biggestPanel.add(titlePanel,BorderLayout.NORTH);
				biggestPanel.add(teamview,BorderLayout.CENTER);
				biggestPanel.validate();
				biggestPanel.repaint();
			}
		});
		playerview.add(lblAddCriterionTo);

		JButton btnSendReport = new JButton("Remove");
		btnSendReport.setHorizontalAlignment(SwingConstants.LEFT);
		btnSendReport.setBounds(178, 184, 74, 29);
		btnSendReport.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (critlist.getSelectedRow()!=-1)
				{
					toCreate.criterionhistory.remove(critlist.convertRowIndexToModel(critlist.getSelectedRow()));
					createPlayerView(toCreate, criterionvalues);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(playerview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		playerview.add(btnSendReport);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
		btnEdit.setBounds(345, 184, 74, 29);
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (critlist.getSelectedRow()!=-1)
				{
					JTextField[] textarray = new JTextField[criterionvalues.size()];
					playereditpanel = new JPanel();
					playereditpanel.setLayout(new FlowLayout());
					for (int i=0; i<toCreate.criterionhistory.get(critlist.convertRowIndexToModel(critlist.getSelectedRow())).size(); i++)
					{
						JTextField ratingtoadd = new JTextField(1);
						ratingtoadd.setText(Integer.toString(toCreate.criterionhistory.get(critlist.convertRowIndexToModel(critlist.getSelectedRow())).get(i)));
						textarray[i] = ratingtoadd;
						playereditpanel.add(ratingtoadd);
					}
					int selection = JOptionPane.showConfirmDialog(playerview, playereditpanel, 
							"Edit player ratings", JOptionPane.OK_CANCEL_OPTION);
					if (selection == JOptionPane.OK_OPTION)
						for (int i=0; i<criterionvalues.size(); i++)
							if (textarray[i].getText().matches("^\\d+$")&&textarray[i].getText().length()<3)
								if (Integer.parseInt(textarray[i].getText())<=10&&Integer.parseInt(textarray[i].getText())>=0)
									toCreate.criterionhistory.get(critlist.convertRowIndexToModel(critlist.getSelectedRow())).set(i,Integer.parseInt(textarray[i].getText()));
					createPlayerView(toCreate, criterionvalues);
					biggestPanel.removeAll();
					biggestPanel.add(titlePanel,BorderLayout.NORTH);
					biggestPanel.add(playerview,BorderLayout.CENTER);
					biggestPanel.validate();
					biggestPanel.repaint();
				}
			}
		});
		playerview.add(btnEdit);
	}
}
