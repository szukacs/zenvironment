import { baseURL } from "@/lib/constans";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";

interface MarketParams {
  vendorId: string;
  profileImage: string;
  gardenName: string;
  description: string;
  productImage: string;
  myGardenId: string;
}

export const MarketPlaceItem = ({
  vendorId,
  profileImage,
  gardenName,
  description,
  productImage,
  myGardenId,
}: MarketParams) => {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const handleClose = () => {
    setIsDialogOpen(false);
  };

  return (
    <>
      <Card
        sx={(theme) => ({
          padding: theme.spacing(1),
        })}
      >
        <CardContent
          sx={(theme) => ({
            padding: theme.spacing(1),
            margin: theme.spacing(1),
          })}
        >
          <Box
            sx={(theme) => ({
              display: "flex",
              marginBottom: theme.spacing(2),
              alignItems: "center",
            })}
          >
            <Box
              component="img"
              sx={(theme) => ({
                maxWidth: "100%",
                width: 60,
                maxHeight: 80,
                objectFit: "contain",
                marginRight: theme.spacing(2),
              })}
              src={`${baseURL}${profileImage}`}
            />
            <Typography variant="h5">{gardenName}</Typography>
          </Box>
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Typography>{description}</Typography>
            <Box
              component="img"
              sx={{
                maxWidth: "100%",
                width: 100,
                maxHeight: 140,
                objectFit: "contain",
              }}
              src={`${baseURL}${productImage}`}
            ></Box>
          </Box>
        </CardContent>
        {myGardenId !== vendorId && (
          <CardActions>
            <Button
              fullWidth
              variant="outlined"
              onClick={() => {
                setIsDialogOpen(true);
              }}
            >
              Make offer
            </Button>
          </CardActions>
        )}
      </Card>
      <Dialog open={isDialogOpen} onClose={handleClose} fullWidth>
        <DialogTitle>{gardenName}</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            multiline
            rows={6}
            label="Type your message..."
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button fullWidth variant="outlined" onClick={handleClose}>
            Make Offer
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
