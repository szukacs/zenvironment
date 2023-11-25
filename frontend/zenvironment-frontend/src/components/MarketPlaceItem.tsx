import { baseURL } from "@/lib/constans";
import {
  Alert,
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

const SendOfferDialog = ({
  open,
  onClose,
  gardenName,
}: {
  open: boolean;
  onClose: () => void;
  gardenName: string;
}) => {
  const [dialogContent, setDialogContent] = useState<"textField" | "alert">(
    "textField"
  );

  const handleClose = () => {
    onClose();
  };

  const handleSubmit = () => {
    setDialogContent("alert");
  };

  return (
    <Dialog open={open} onClose={handleClose} fullWidth>
      <DialogTitle>Message to {gardenName}</DialogTitle>
      <DialogContent>
        {dialogContent === "textField" ? (
          <TextField
            sx={{marginTop: '1rem'}}
            autoFocus
            multiline
            rows={6}
            label="Type your message..."
            fullWidth
          />
        ) : (
          <Alert severity="success">Message sent</Alert>
        )}
      </DialogContent>
      <DialogActions>
        {dialogContent === "textField" ? (
          <Button fullWidth variant="outlined" onClick={handleSubmit}>
            Make Offer
          </Button>
        ) : (
          <Button fullWidth variant="outlined" onClick={handleClose}>
            Close
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

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
      {isDialogOpen && (
        <SendOfferDialog
          open={isDialogOpen}
          onClose={handleClose}
          gardenName={gardenName}
        />
      )}
    </>
  );
};
