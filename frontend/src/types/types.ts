export type TUserAuth = {
  username: string;
  password: string;
};

export type TUserRegister = {
  email: string;
  firstName: string;
  lastName: string;
  enrollment: string;
  dni: string;
  username: string;
  password: string;
  confirmPassword: string;
  type: string;
  phoneNumber?: string;
  birthday?: string;
  address?: {
    province?: string;
    department?: string;
    locality?: string;
    address?: string;
    latitude?: number;
    longitude?: number;
  };
};
