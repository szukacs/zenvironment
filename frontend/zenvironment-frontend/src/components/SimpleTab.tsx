import React from 'react';
import {Box, Tab, Tabs} from "@mui/material";

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export interface DisplayTab {
  label: string,
  content: React.ReactNode
}

export interface SimpleTabProps {
  defaultTab?: number;
  tabs: DisplayTab[]
  children?: React.ReactNode;
}

export const SimpleTab: React.FC<SimpleTabProps> = ({tabs, defaultTab = 0}) => {
  const [value, setValue] = React.useState(defaultTab);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ width: '100%' }}>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs
          value={value}
          onChange={handleChange}
          textColor="secondary"
          indicatorColor="secondary"
          aria-label="basic tabs example">
          {
            tabs
              .map((displayTab, index) => (
                <Tab key={displayTab.label} label={displayTab.label} {...a11yProps(index)} />
              ))
          }
        </Tabs>
      </Box>
      {tabs.map((displayTab, index) => (
        <TabPanel key={displayTab.label} value={value} index={index}>
          {displayTab.content}
        </TabPanel>
      ))}
    </Box>
  );
}

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

const TabPanel: React.FC<TabPanelProps> = ({children, value, index, ...other}) => {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{p: 3}}>
          {children}
        </Box>
      )}
    </div>
  );
}
