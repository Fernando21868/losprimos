"use client";
import "../../../node_modules/leaflet/dist/leaflet.css";
import marker from "../../../node_modules/leaflet/dist/images/marker-icon.png";
import { Loader2 } from "lucide-react";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Button } from "../../@components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../@components/ui/form";
import { Input } from "../../@components/ui/input";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { StateAuthSlice } from "../../types/interfaces";
import { RootState } from "../../store/store";
import { registerUser } from "../../data/authActions";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import { Checkbox } from "../../@components/ui/checkbox";
import { toast } from "../../@components/ui/use-toast";
import { ToastAction } from "../../@components/ui/toast";
import { alreadyRegister } from "../../store/authSlice";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../@components/ui/select";
import { useAddress } from "../../hooks/useAddress";
import {
  MapContainer,
  Marker,
  Popup,
  TileLayer,
  useMapEvents,
} from "react-leaflet";
import { Icon } from "leaflet";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "../../@components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../../@components/ui/card";
import { Toaster } from "../../@components/ui/toaster";

type TDataAddress = {
  id: string;
  nombre: string;
};

function Register() {
  const { loading, userInfo, success }: StateAuthSlice = useSelector(
    (state: RootState) => state["auth"]
  );
  const dispatch = useDispatch<ThunkDispatch<RootState, any, AnyAction>>();
  const navigate = useNavigate();
  const {
    getProvinces,
    getDepartments,
    getMunicipalities,
    loading: loadingAddress,
  } = useAddress();

  const [latitude, setLatitude] = useState<number | undefined>(undefined);
  const [longitude, setLongitude] = useState<number | undefined>(undefined);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [provinces, setProvinces] = useState<null | Array<TDataAddress>>();
  const [departaments, setDepartaments] =
    useState<null | Array<TDataAddress>>();
  const [municipalities, setmunicipalities] =
    useState<null | Array<TDataAddress>>();

  const myIcon = new Icon({
    iconUrl: marker,
    iconSize: [32, 32],
  });

  useEffect(() => {
    if (userInfo) {
      navigate("/");
    }
    if (success) navigate("/login");

    async function fetchProvinces() {
      const { provincias: provinces } = await getProvinces();
      setProvinces(provinces);
    }
    fetchProvinces();
  }, [navigate, userInfo, success]);

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: "Ferarias33",
      lastName: "Ferarias33",
      email: "39808901@fi.unju.edu.ar",
      username: "Ferarias33",
      password: "Ferarias33",
      confirmPassword: "Ferarias33",
      phoneNumber: "123123",
      birthday: "2023-08-11",
      address: {
        province: "Jujuy",
        department: "Dr. Manuel Belgrano",
        municipality: "Vinalito",
        address: "Adresss",
        latitude: undefined,
        longitude: undefined,
      },
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    if (latitude && longitude) {
      values.address!.latitude = latitude;
      values.address!.longitude = longitude;
    }

    const response = await dispatch(registerUser(values));

    if (response.type === "auth/register/rejected") {
      toast({
        variant: "destructive",
        title: "Algo ocurrio mal.",
        description: response.payload as string,
        action: (
          <ToastAction altText="Cerrar">
            Intentar nuevamente
          </ToastAction>
        ),
      });
    } else {
      dispatch(alreadyRegister());
      navigate("/confirmationEmail");
    }
  }

  function handleShowPassword() {
    setShowPassword(!showPassword);
  }

  function handleShowConfirmPassword() {
    setShowConfirmPassword(!showConfirmPassword);
  }

  async function handleChangePronvice(value: string) {
    const { departamentos: departaments } = await getDepartments(value);
    setDepartaments(departaments);
  }

  async function handleChangeProvinceGetMunicipalities(value: string) {
    const { municipios: municipalities } = await getMunicipalities(value);
    setmunicipalities(municipalities);
  }

  const LocationFinderDummy = () => {
    useMapEvents({
      click(e) {
        setLatitude(e.latlng.lat);
        setLongitude(e.latlng.lng);
      },
    });
    return null;
  };

  return (
    <div className="p-4 flex flex-col items-center justify-center">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="max-w-md w-full"
        >
          <Toaster></Toaster>
          <Tabs defaultValue="account" className="max-w-md w-full">
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="account">Campos obligatorios</TabsTrigger>
              <TabsTrigger value="password">Campos opcionales</TabsTrigger>
            </TabsList>
            <TabsContent value="account">
              <Card>
                <CardHeader>
                  <CardTitle>Campos obligatorios</CardTitle>
                  <CardDescription>
                    Los siguientes campos son todos obligatorios para crear una
                    cuenta.
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-2">
                  <FormField
                    control={form.control}
                    name="firstName"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Nombres</FormLabel>
                        <FormControl>
                          <Input placeholder="Por ejemplo: Lucas" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="lastName"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Apellidos</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Por ejemplo: Rodriguez"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>E-mail</FormLabel>
                        <FormControl>
                          <Input
                            type="email"
                            placeholder="Por ejemplo: lucasRodriguez12@gmail.com"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="username"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Usuario</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Por ejemplo: Usuario"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="password"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Contraseña</FormLabel>
                        <FormDescription>
                          La contraseña debe cotener por lo menos 4 caracteres,
                          1 mayuscula, 1 minuscula, y 1 numero
                        </FormDescription>
                        <FormControl>
                          <Input
                            type={showPassword ? "text" : "password"}
                            placeholder="Por ejemplo: Lucas123"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <div className="-mt-2 mb-4 flex items-center space-x-2">
                    <Checkbox onClick={handleShowPassword} id="showPassword" />
                    <label
                      htmlFor="showPassword"
                      className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                    >
                      Mostrar/ocultar contraseña
                    </label>
                  </div>
                  <FormField
                    control={form.control}
                    name="confirmPassword"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Confirmar contraseña</FormLabel>
                        <FormControl>
                          <Input
                            type={showConfirmPassword ? "text" : "password"}
                            placeholder="Por ejemplo: Lucas123"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <div className="-mt-2 mb-4 flex items-center space-x-2">
                    <Checkbox
                      onClick={handleShowConfirmPassword}
                      id="showConfirmPassowd"
                    />
                    <label
                      htmlFor="showConfirmPassowd"
                      className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                    >
                      Mostrar/ocultar contraseña
                    </label>
                  </div>
                </CardContent>
                <CardFooter className="flex flex-col justify-start items-start">
                  {loading ? (
                    <Button className="mt-4" disabled>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Por favor espere
                    </Button>
                  ) : (
                    <Button className="mt-4" type="submit">
                      Registrarse
                    </Button>
                  )}
                  <div>
                    ¿Tienes una cuenta?
                    <Button className="-ml-2" variant="link">
                      <Link to={"/login"}>Inicia sesion</Link>
                    </Button>
                  </div>
                </CardFooter>
              </Card>
            </TabsContent>
            <TabsContent value="password">
              <Card>
                <CardHeader>
                  <CardTitle>Campos opcionales</CardTitle>
                  <CardDescription>
                    Los siguientes campos son opcionales y podra cambiarlos o
                    llenarlos cuando desee.
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-2">
                  <FormField
                    control={form.control}
                    name="phoneNumber"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Telefono o celular</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Por ejemplo: 3886905902"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="birthday"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Fecha de nacimiento</FormLabel>
                        <FormControl>
                          <Input
                            type="date"
                            placeholder="Por ejemplo: 3886905902"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="address.address"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Direccion</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Por ejemplo: Jujuy 123"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="address.province"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Provincia</FormLabel>
                        <Select
                          onValueChange={(value: string) => {
                            field.onChange(value);
                            handleChangePronvice(value);
                            handleChangeProvinceGetMunicipalities(value);
                          }}
                          defaultValue={field.value}
                          disabled={loadingAddress || !provinces ? true : false}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder="Seleccione una provincia" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent className="h-[200px] overflow-auto z-50">
                            {provinces?.map((province) => {
                              return (
                                <SelectItem
                                  key={province.id}
                                  value={province.nombre}
                                >
                                  {province.nombre}
                                </SelectItem>
                              );
                            })}
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="address.department"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Departamento</FormLabel>
                        <Select
                          onValueChange={field.onChange}
                          defaultValue={field.value}
                          disabled={
                            loadingAddress || !departaments ? true : false
                          }
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder="Seleccione un departamento" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent className="h-[200px] overflow-auto z-50">
                            {departaments?.map((departament) => {
                              return (
                                <SelectItem
                                  key={departament.id}
                                  value={departament.nombre}
                                >
                                  {departament.nombre}
                                </SelectItem>
                              );
                            })}
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="address.municipality"
                    render={({ field }) => (
                      <FormItem className="mb-4">
                        <FormLabel>Municipio</FormLabel>
                        <Select
                          onValueChange={field.onChange}
                          defaultValue={field.value}
                          disabled={
                            loadingAddress || !municipalities ? true : false
                          }
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder="Seleccione un municipio" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent className="h-[200px] overflow-auto z-50">
                            {municipalities?.map((municipality) => {
                              return (
                                <SelectItem
                                  key={municipality.id}
                                  value={municipality.nombre}
                                >
                                  {municipality.nombre}
                                </SelectItem>
                              );
                            })}
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormLabel>Domicilio</FormLabel>
                  <MapContainer
                    center={[-24.19457, -65.29712]}
                    zoom={13}
                    scrollWheelZoom={false}
                    style={{
                      height: "300px",
                      width: "100%",
                      zIndex: "10",
                    }}
                  >
                    <LocationFinderDummy />
                    <TileLayer
                      attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    <Marker
                      position={[latitude ?? -24.19457, longitude ?? -65.29712]}
                      icon={myIcon}
                    >
                      <Popup>
                        Domicilio en
                        <br />
                        Latitud: {latitude ?? -24.19457}
                        <br />
                        Longitud: {longitude ?? -65.29712}
                      </Popup>
                    </Marker>
                  </MapContainer>
                </CardContent>
                <CardFooter className="flex flex-col justify-start items-start">
                  {loading ? (
                    <Button className="mt-4" disabled>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Por favor espere
                    </Button>
                  ) : (
                    <Button className="mt-4" type="submit">
                      Registrarse
                    </Button>
                  )}
                  <div>
                    ¿Tienes una cuenta?
                    <Button className="-ml-2" variant="link">
                      <Link to={"/login"}>Inicia sesion</Link>
                    </Button>
                  </div>
                </CardFooter>
              </Card>
            </TabsContent>
          </Tabs>
        </form>
      </Form>
    </div>
  );
}

const formSchema = z
  .object({
    firstName: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    lastName: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    username: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    password: z
      .string()
      .nonempty({
        message: "Debe ingresar una contraseña",
      })
      .max(30, {
        message: "La contraseña no debe superar los 30 caracteres",
      })
      .regex(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{4,}$/, {
        message:
          "La contraseña debe cotener por lo menos 4 caracteres, 1 mayuscula, 1 minuscula, y 1 numero",
      })
      .trim(),
    confirmPassword: z
      .string()
      .nonempty({
        message: "Debe ingresar una contraseña",
      })
      .max(30, {
        message: "La contraseña no debe superar los 30 caracteres",
      })
      .regex(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{4,}$/, {
        message:
          "La contraseña debe cotener por lo menos 4 caracteres, 1 mayuscula, 1 minuscula, y 1 numero",
      })
      .trim(),
    email: z
      .string()
      .nonempty({
        message: "Debe ingresar un e-mail",
      })
      .max(100, {
        message: "El email no debe superar los 100 caracteres",
      })
      .email({
        message: "Ingrese un email valido",
      })
      .trim(),
    birthday: z.string().optional(),
    phoneNumber: z
      .string()
      .max(30, {
        message: "El numero de telefono no debe superar los 100 caracteres",
      })
      .trim()
      .optional(),
    address: z
      .object({
        address: z
          .string()
          .max(100, {
            message: "La direccion no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        province: z
          .string()
          .max(100, {
            message: "La provincia no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        department: z
          .string()
          .max(100, {
            message: "El departamento no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        municipality: z
          .string()
          .max(100, {
            message: "La localidad no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        latitude: z.number().optional(),
        longitude: z.number().optional(),
      })
      .optional(),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "Las contraseñas no coinciden",
      });
    }
  });

export default Register;
