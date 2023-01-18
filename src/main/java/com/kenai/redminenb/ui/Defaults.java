/*
 * Copyright 2012 Anchialas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kenai.redminenb.ui;

import com.kenai.redminenb.RedmineConfig;
import com.kenai.redminenb.query.ParameterValue;
import com.kenai.redminenb.user.RedmineUser;
import com.kenai.redminenb.util.AssigneeWrapper;
import com.kenai.redminenb.util.NestedProject;
import com.taskadapter.redmineapi.bean.IssueCategory;
import com.taskadapter.redmineapi.bean.IssuePriority;
import com.taskadapter.redmineapi.bean.IssueStatus;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.TimeEntryActivity;
import com.taskadapter.redmineapi.bean.Tracker;
import com.taskadapter.redmineapi.bean.Version;
import com.taskadapter.redmineapi.bean.Watcher;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import org.openide.util.ImageUtilities;

/**
 * RedmineNB UI Defaults.
 *
 * @author Anchialas <anchialas@gmail.com>
 */
public class Defaults {

    public static final String TOGGLE_BUTTON_KEY = "isToggleButton";
    //
    public static final Color COLOR_ERROR = new Color(153, 0, 0);
    private static final Color COLOR_TOP = new Color(198, 211, 223);
    private static final Color COLOR_BOTTOM = new Color(235, 235, 235);

    private Defaults() {
        // suppressed for non-instantiability
    }

    public static Icon getIcon(String iconBaseName) {
        return ImageUtilities.loadImageIcon("com/kenai/redminenb/resources/" + iconBaseName, false);
    }
    
    public static Image getImage(String iconBaseName) {
        return ImageUtilities.loadImage("com/kenai/redminenb/resources/" + iconBaseName, false);
    }

    public static void setBoldFont(JLabel label) {
        Font f = label.getFont().deriveFont(Font.BOLD);
        label.setFont(f);
    }

    public static Graphics2D paintGradient(Graphics2D g2d, int width, int height) {
        g2d.setPaint(new GradientPaint(0, 0, COLOR_TOP, 0, height, COLOR_BOTTOM));
        g2d.fillRect(0, 0, width, height);
        return g2d;
    }

    public static class TrackerLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Tracker) {
                value = ((Tracker) value).getName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class IssueStatusLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                value = ((IssueStatus) value).getName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class IssueCategoryLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof IssueCategory) {
                value = ((IssueCategory) value).getName();
            } else {
                if (value == null) {
                    value = " ";
                }
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class VersionLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Version) {
                value = ((Version) value).getName();
            } else {
                if (value == null) {
                    value = " ";
                }
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class RepositoryUserLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            boolean isCurrentUser = false;
            boolean notNull = false;
            if (value instanceof ParameterValue) {
                ParameterValue pv = (ParameterValue) value;
                value = pv.getDisplayName();
            }

            if (value instanceof RedmineUser) {
                RedmineUser user = (RedmineUser) value;
                value = user.toString();
                isCurrentUser = user.isIsCurrentUser();
                notNull = true;
            } else if (value instanceof AssigneeWrapper) {
                AssigneeWrapper assignee = (AssigneeWrapper) value;
                value = assignee.getName();
                isCurrentUser = assignee.isIsCurrentUser();
                notNull = true;
            } else if (value == null) {
                value = " ";
            }
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(notNull) {
                ((JLabel) c).setIcon(Defaults.getIcon(isCurrentUser ? "user.png" : "user_gray.png"));
            }
            return c;
        }
    }

    public static class PriorityLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof IssuePriority) {
                value = ((IssuePriority) value).getName();
            } else if (value instanceof ParameterValue) {
                value = ((ParameterValue) value).getDisplayName();
            } else if (value == null) {
                value = " ";
            } else {
                value = value.toString();
            }

            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ((JLabel) c).setIcon(RedmineConfig.getInstance().getPriorityIcon((String) value));
            return c;
        }
    }

    public static class ParameterValueLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof ParameterValue) {
                value = ((ParameterValue) value).getDisplayName();
                if (value == null) {
                    return new JSeparator();
                }
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class TimeEntryActivityLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof TimeEntryActivity) {
                value = ((TimeEntryActivity) value).getName();
            } else {
                if (value == null) {
                    value = " ";
                }
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static class WatcherLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Watcher) {
                Watcher w = (Watcher) value;
                value = String.format("%s (%d)", w.getName(), w.getId());
            } else {
                if (value == null) {
                    value = " ";
                }
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
    
    public static class ProjectLCR extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof NestedProject) {
                value = ((NestedProject) value).toString();
            } else if (value instanceof Project) {
                value = ((Project) value).getName();
            } else if (value == null) {
                value = " ";
            } else {
                value = value.toString();
                if("".equals(value)) {
                    value = " ";
                }
            }

            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ((JLabel) c).setIcon(RedmineConfig.getInstance().getPriorityIcon((String) value));
            return c;
        }
    }
    
    public static class PercentLCR extends DefaultListCellRenderer {

        public PercentLCR() {
            ((JLabel) this).setHorizontalAlignment(JLabel.RIGHT);
        }
    }
}
